package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.BusinessException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final ScheduleRepository scheduleRepository;
    private final ReservationMapper reservationMapper;

    public void bulkBookReservations(List<CreateReservationRequestDTO> createReservationRequestDTOS) {
        Validate.notEmpty(createReservationRequestDTOS, "The list can't empty");
        createReservationRequestDTOS.forEach(this::bookReservation);
    }

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        Validate.notNull(createReservationRequestDTO.getGuestId(), "Guest id can't be null");
        Validate.notNull(createReservationRequestDTO.getScheduleId(), "Schedule id can't be null");

        Guest guest = guestRepository.findById(createReservationRequestDTO.getGuestId()).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });

        Schedule schedule = scheduleRepository.findById(createReservationRequestDTO.getScheduleId()).orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found");
        });

        if (!CollectionUtils.isEmpty(schedule.getReservations())) {
            throw new BusinessException("Schedule is not free");
        }

        Reservation reservation = Reservation.builder()
                .guest(guest)
                .schedule(schedule)
                .reservationStatus(ReservationStatus.READY_TO_PLAY)
                .value(BigDecimal.valueOf(10))
                .build();

        reservationRepository.save(reservation);
        schedule.addReservation(reservation);
        scheduleRepository.save(schedule);

        return reservationMapper.map(reservation);
    }

    public ReservationDTO findReservation(Long reservationId) {
        return reservationMapper.map(findReservationOrThrow(reservationId));
    }

    private Reservation findReservationOrThrow(Long reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(() -> {
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
            Schedule schedule = reservation.getSchedule();
            schedule.setReservations(new ArrayList<>());
            scheduleRepository.save(schedule);

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

        if (hours >= 24) {
            return reservation.getValue();
        }

        if (hours >= 12) {
            return reservation.getValue().multiply(BigDecimal.valueOf(0.75));
        }

        if (hours >= 2) {
            return reservation.getValue().multiply(BigDecimal.valueOf(0.5));
        }

        return reservation.getValue().multiply(BigDecimal.valueOf(0.25));
    }
    
    public ReservationDTO rescheduleReservation(Long previousReservationId, Long newScheduleId) {
        Reservation previousReservation = findReservationOrThrow(previousReservationId);

        if (newScheduleId.equals(previousReservation.getSchedule().getId())) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }

        previousReservation = cancel(previousReservationId);
        previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
        reservationRepository.save(previousReservation);

        ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO.builder()
                .guestId(previousReservation.getGuest().getId())
                .scheduleId(newScheduleId)
                .build());
        newReservation.setPreviousReservation(reservationMapper.map(previousReservation));
        return newReservation;
    }

    public ReservationDTO markAsCompleted(Long reservationId) {
        Validate.notNull(reservationId, "Reservation id can't be null");
        Reservation reservation = findReservationOrThrow(reservationId);

        Validate.isTrue(reservation.getReservationStatus().equals(ReservationStatus.READY_TO_PLAY),
                "Invalid reservation status change");

        reservation.setReservationStatus(ReservationStatus.COMPLETED);
        reservation.setRefundValue(reservation.getValue());
        reservation.setValue(BigDecimal.ZERO);

        reservationRepository.save(reservation);

        return reservationMapper.map(reservation);
    }
}
