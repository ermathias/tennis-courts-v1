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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
            .value(RESERVATION_DEPOSIT_VALUE)
            .refundValue(BigDecimal.ZERO)
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

        boolean existsSavedReservations = reservationRepository.existsByScheduleAndReservationStatus(savedSchedule, ReservationStatus.READY_TO_PLAY);

        if (existsSavedReservations) {
            throw new IllegalArgumentException("Cannot have two reservations ready to play for a given schedule.");
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
        BigDecimal reservationValue = reservation.getValue();
        BigDecimal chargedValue = BigDecimal.ZERO;
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());

        if (hours < 2L) {
            chargedValue = reservationValue.multiply(new BigDecimal(0.75));
        } else if (hours < 12L) {
            chargedValue = reservationValue.multiply(new BigDecimal(0.5));
        }  else if (hours < 24L) {
            chargedValue = reservationValue.multiply(new BigDecimal(0.25));
        }

        return reservationValue.subtract(chargedValue);
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

    @Transactional
    public ReservationDTO noShow(Long reservationId) {
        Reservation savedReservation = reservationRepository.findById(reservationId).orElse(null);

        validateNoShow(savedReservation);

        savedReservation.setReservationStatus(ReservationStatus.NO_SHOW);
        savedReservation.setValue(savedReservation.getValue());
        savedReservation.setRefundValue(BigDecimal.ZERO);

        return reservationMapper.map(reservationRepository.save(savedReservation));
    }

    public List<ReservationDTO> getHistory(LocalDate startDate, LocalDate endDate) {
        return reservationMapper.map(reservationRepository.getHistory(startDate, endDate));
    }

    private void validateNoShow(Reservation savedReservation) {
        if (savedReservation == null) {
            throw new EntityNotFoundException("Reservation not found.");
        }

        if (savedReservation.getReservationStatus() != ReservationStatus.READY_TO_PLAY) {
            throw new IllegalArgumentException("Cannot set the reservation as no show because it's not in ready to play status.");
        }

        if (savedReservation.getSchedule().getStartDateTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot set the reservation as no show because it's in the future.");
        }
    }
}
