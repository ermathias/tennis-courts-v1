package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.schedules.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {

    @Autowired
    private final ReservationRepository reservationRepository;

    @Autowired
    private final GuestRepository guestRepository;

    @Autowired
    private final ScheduleRepository scheduleRepository;

    @Autowired
    private final ReservationMapper reservationMapper;

    public List<ReservationDTO> findAllReservations() {
        return reservationMapper.map(reservationRepository.findAll());
    }

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        if(!guestRepository.existsById(createReservationRequestDTO.getGuestId())){
            throw new EntityNotFoundException("Guest does not exist");
        }
        if(!scheduleRepository.existsById(createReservationRequestDTO.getGuestId())){
            throw  new EntityNotFoundException("Schedule does not exist");
        }
        return reservationMapper.map(reservationRepository.save(Reservation.builder()
                .guest(guestRepository.findById(createReservationRequestDTO.getGuestId()).get())
                .schedule(scheduleRepository.findById(createReservationRequestDTO.getScheduleId()).get())
                .reservationStatus( ReservationStatus.READY_TO_PLAY )
                .refundValue( new BigDecimal( 10 ) )
                .value( new BigDecimal( 10 ) )
                .build()));
    }

    public ReservationDTO findReservation(Long reservationId) throws EntityNotFoundException {
        try {
            return reservationRepository.findById(reservationId).map(reservationMapper::map).orElseThrow(() -> {
                throw new EntityNotFoundException("Reservation not found.");
            });
        } catch (Throwable t){
            throw new EntityNotFoundException(t.getMessage());
        }
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        return reservationMapper.map(this.cancel(reservationId));
    }

    private Reservation cancel(Long reservationId) throws EntityNotFoundException {
        try {
            return reservationRepository.findById(reservationId).map(reservation -> {

                this.validateCancellation(reservation);

                BigDecimal refundValue = getRefundValue(reservation);
                return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);

            }).orElseThrow(() -> {
                throw new EntityNotFoundException("Reservation not found.");
            });
        } catch (Throwable t) {
            throw new EntityNotFoundException(t.getMessage());
        }
    }

    private Reservation updateReservation(Reservation reservation, BigDecimal refundValue, ReservationStatus status) {
        reservation.setReservationStatus(status);
        reservation.setValue(reservation.getValue().subtract(refundValue));
        reservation.setRefundValue(refundValue);

        return reservationRepository.save(reservation);
    }

    private void validateCancellation(Reservation reservation) {
        if (!ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
            throw new IllegalArgumentException("Cannot cancel/reschedule because it's not in ready to play status.");
        }

        if (reservation.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Can cancel/reschedule only future dates.");
        }
    }

    public BigDecimal getRefundValue(Reservation reservation) {
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());

        if (hours >= 24) {
            return reservation.getValue();
        }

        return BigDecimal.ZERO;
    }

    /*TODO: This method actually not fully working, find a way to fix the issue when it's throwing the error:
            "Cannot reschedule to the same slot.*/
    @Transactional( rollbackFor = Exception.class )
    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = cancel(previousReservationId);

        if (scheduleId.equals(previousReservation.getSchedule().getId())) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }

        previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
        reservationRepository.save(previousReservation);

        ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO.builder()
                .guestId(previousReservation.getGuest().getId())
                .scheduleId(scheduleId)
                .build());
        newReservation.setPreviousReservation(reservationMapper.map(previousReservation));
        return newReservation;
    }
}
