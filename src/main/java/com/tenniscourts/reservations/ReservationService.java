package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestService;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleDTO;
import com.tenniscourts.schedules.ScheduleService;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.inject.Inject;
import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class ReservationService {

    private static BigDecimal RESERVATION_DEPOSIT_VALUE = BigDecimal.TEN;

    @Inject
    private final ReservationRepository reservationRepository;

    @Inject
    private final ReservationMapper reservationMapper;

    @Inject
    private final ScheduleService scheduleService;

    @Inject
    private final GuestService guestService;

    @Transactional
    public ReservationDTO bookReservation(CreateReservationRequestDTO reservationDTO) {
        Schedule savedSchedule = scheduleService.findById(reservationDTO.getScheduleId());
        Guest savedGuest = guestService.findById(reservationDTO.getGuestId());

        validateReservation(savedSchedule, savedGuest);

        Reservation reservation = Reservation.builder()
            .guest(savedGuest)
            .schedule(savedSchedule)
            .reservationStatus(ReservationStatus.READY_TO_PLAY)
            .value(BigDecimal.ZERO)
            .refundValue(RESERVATION_DEPOSIT_VALUE)
            .build();

        return reservationMapper.map(reservationRepository.save(reservation));
    }

    private void validateReservation(Schedule savedSchedule, Guest savedGuest) {
        if (savedSchedule == null) {
            throw new EntityNotFoundException("Schedule not found.");
        }

        if (savedGuest == null) {
            throw new EntityNotFoundException("Guest not found.");
        }
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
        BigDecimal refundValue = reservation.getRefundValue();
        BigDecimal chargedValue = BigDecimal.ZERO;
        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());

        if (minutes < 120) {
            chargedValue = refundValue.multiply(new BigDecimal(75));
        } else if (minutes < 720) {
            chargedValue = refundValue.multiply(new BigDecimal(50));
        }  else if (minutes < 1440) {
            chargedValue = refundValue.multiply(new BigDecimal(25));
        }

        return refundValue.subtract(chargedValue);
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