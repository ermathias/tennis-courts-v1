package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.ScheduleDTO;
import com.tenniscourts.schedules.ScheduleService;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class ReservationService {

    @Inject
    private final ReservationRepository reservationRepository;

    @Inject
    private final ReservationMapper reservationMapper;

    @Inject
    private final ScheduleService scheduleService;

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        Reservation reservation = Reservation.builder().reservationStatus(ReservationStatus.READY_TO_PLAY).build();
        return reservationMapper.map(reservationRepository.save(reservation));
    }

    public ReservationDTO findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservationMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    @Transactional
    public ReservationDTO cancelReservation(Long reservationId) {
        return reservationMapper.map(cancel(reservationId));
    }

    private Reservation cancel(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {
            validateCancellation(reservation);

            BigDecimal refundValue = getRefundValue(reservation);
            return updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);
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

        if (hours >= 24) {
            return reservation.getValue();
        }

        return BigDecimal.ZERO;
    }

    private void validateSameTimeSlot(Reservation reservation, ScheduleDTO schedule) throws IllegalArgumentException {
        if (reservation.getSchedule().getStartDateTime().equals(schedule.getStartDateTime())) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }
    }

    @Transactional
    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = cancel(previousReservationId);
        ScheduleDTO savedSchedule = scheduleService.findSchedule(scheduleId);

        validateSameTimeSlot(previousReservation, savedSchedule);

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