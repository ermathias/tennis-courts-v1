package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.schedules.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    private final ScheduleRepository scheduleRepository;

    private final GuestRepository guestRepository;

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {

        var schedule = scheduleRepository.findById(createReservationRequestDTO.getScheduleId());
        var guest = guestRepository.findById(createReservationRequestDTO.getGuestId());

        if (schedule.isPresent() && guest.isPresent()) {
            var reservation = Reservation.builder()
                    .guest(guest.get())
                    .schedule(schedule.get())
                    .value(new BigDecimal("100.00").add(new BigDecimal("10.00")))
                    .reservationStatus(ReservationStatus.READY_TO_PLAY)
                    .refundValue(new BigDecimal("0.00"))
                    .build();

            schedule.get().addReservation(reservation);
            return reservationMapper.map(reservationRepository.saveAndFlush(reservation));
        } else {
            throw new EntityNotFoundException("Schedule or Guest not found");
        }
    }

    public ReservationDTO findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservationMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        return reservationMapper.map(this.cancel(reservationId));
    }

    private Reservation cancel(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {

            this.validateCancellation(reservation);

            BigDecimal refundValue = getRefundValue(reservation);
            return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);

        }).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
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
        BigDecimal refundValue = reservation.getValue();

        if (hours >= 24) {
            return refundValue;
        } else if (hours >= 12 && hours <= 23) {
            return refundValue.divide(new BigDecimal("4")).multiply(new BigDecimal("3"));
        } else if (hours >= 2 && hours <= 11) {
            return refundValue.divide(new BigDecimal("4")).multiply(new BigDecimal("2"));
        } else if (hours >= 0 && hours <= 2) {
            return refundValue.divide(new BigDecimal("4"));
        }

        return BigDecimal.ZERO;
    }

    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = cancel(previousReservationId);

        if (scheduleId.equals(previousReservation.getSchedule().getId())) {
            previousReservation.setReservationStatus(ReservationStatus.READY_TO_PLAY);
            reservationRepository.save(previousReservation);
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

    public List<ReservationDTO> getAllMyReservations(Long id) {
        var guest = guestRepository.findById(id);
        if (guest.isPresent()) {
            List<Reservation> reservationList = reservationRepository.findByGuestIdAndScheduleStartDateTimeLessThan(id, LocalDateTime.now());
            List<ReservationDTO> reservationDTOList = new ArrayList<>();
            for (Reservation reservation : reservationList) {
                reservationDTOList.add(reservationMapper.map(reservation));
            }
            return reservationDTOList;
        }
        throw new EntityNotFoundException("Guest not found.");
    }

}
