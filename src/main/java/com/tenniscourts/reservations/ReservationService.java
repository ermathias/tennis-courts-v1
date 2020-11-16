package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.schedules.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.tenniscourts.reservations.ReservationStatus.READY_TO_PLAY;

@Service
@AllArgsConstructor
public class ReservationService {

    @Autowired
    private final ReservationMapper reservationMapper;

    @Autowired
    private final GuestRepository guestRepository;
    @Autowired
    private final ReservationRepository reservationRepository;
    @Autowired
    private final ScheduleRepository scheduleRepository;

    @Transactional(rollbackFor = Exception.class)
    public ReservationDTO bookReservation(CreateReservationRequestDTO dto) {
        Reservation reservation = Reservation.builder()
                .schedule(scheduleRepository.findById(dto.getScheduleId())
                        .orElseThrow(() -> new EntityNotFoundException("Schedule not found.")))
                .guest(guestRepository.findById(dto.getGuestId())
                        .orElseThrow(() -> new EntityNotFoundException("Guest not found.")))
                .reservationStatus(READY_TO_PLAY).value(BigDecimal.TEN)
                .refundValue(BigDecimal.TEN).build();

        return reservationMapper.map(reservationRepository.save(reservation));
    }

    public List<ReservationDTO> findAll() {
        return reservationMapper.map(reservationRepository.findAll());
    }

    public ReservationDTO findReservationById(Long id) {
        return reservationRepository.findById(id).map(reservationMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found."));
    }

    public ReservationDTO cancelReservation(Long id) {
        return reservationMapper.map(this.cancel(id));
    }

    private Reservation cancel(Long id) {
        return reservationRepository.findById(id).map(reservation -> {
            this.validateCancellation(reservation);
            BigDecimal refundValue = getRefundValue(reservation);

            return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);
        }).orElseThrow(() -> new EntityNotFoundException("Reservation not found."));
    }

    private Reservation updateReservation(Reservation reservation, BigDecimal refundValue, ReservationStatus status) {
        reservation.setReservationStatus(status);
        reservation.setValue(reservation.getValue().subtract(refundValue));
        reservation.setRefundValue(refundValue);

        return reservationRepository.save(reservation);
    }

    private void validateCancellation(Reservation reservation) {
        if (!READY_TO_PLAY.equals(reservation.getReservationStatus()))
            throw new IllegalArgumentException("Cannot cancel/reschedule because it's not in ready to play status.");

        if (reservation.getSchedule().getStartDateTime().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Can cancel/reschedule only future dates.");
    }

    public ReservationDTO missedReservation(Long reservationId) {
        return reservationMapper.map(missed(reservationId));
    }

    private Reservation missed(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {
            validateNoShow(reservation);
            BigDecimal refundValue = BigDecimal.ZERO;

            return updateReservation(reservation, refundValue, ReservationStatus.MISSED);
        }).orElseThrow(() -> new EntityNotFoundException("Reservation not found."));
    }

    private void validateNoShow(Reservation reservation) {
        if (!READY_TO_PLAY.equals(reservation.getReservationStatus()))
            throw new IllegalArgumentException("Cannot change because status isn't Ready to Play.");

        if (reservation.getSchedule().getStartDateTime().isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("Can only change past dates.");
    }

    public ReservationDTO finishReservation(Long reservationId) {
        return reservationMapper.map(finish(reservationId));
    }

    private Reservation finish(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {
            validateFinished(reservation);
            BigDecimal refundValue = reservation.getValue();

            return updateReservation(reservation, refundValue, ReservationStatus.FINISHED);
        }).orElseThrow(() -> new EntityNotFoundException("Reservation not found."));
    }

    private void validateFinished(Reservation reservation) {
        if (!READY_TO_PLAY.equals(reservation.getReservationStatus()))
            throw new IllegalArgumentException("Cannot change because status isn't Ready to Play.");

        if (reservation.getSchedule().getStartDateTime().isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("Can only change past dates.");
    }

    public BigDecimal getRefundValue(Reservation reservation) {
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());

        if (hours >= 24)
            return reservation.getValue();
        else if (hours <= 2)
            return reservation.getValue().multiply(new BigDecimal(0.25));
        else if (hours < 12)
            return reservation.getValue().multiply(new BigDecimal(0.5));
        else if (hours < 24)
            return reservation.getValue().multiply(new BigDecimal(0.75));

        return BigDecimal.ZERO;
    }

    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = cancel(previousReservationId);

        if (scheduleId.equals(previousReservation.getSchedule().getId()))
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");

        previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
        reservationRepository.save(previousReservation);

        ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO
                .builder()
                .guestId(previousReservation.getGuest().getId())
                .scheduleId(scheduleId)
                .build());
        newReservation.setPreviousReservation(reservationMapper.map(previousReservation));

        return newReservation;
    }

}
