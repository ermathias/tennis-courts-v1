package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.KiranjyothiPRDTO;
import com.tenniscourts.guests.KiranjyothiPRMapper;
import com.tenniscourts.guests.KiranjyothiPRRepository;
import com.tenniscourts.schedules.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.function.BiPredicate;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    private final KiranjyothiPRRepository guestRepository;

    private final KiranjyothiPRMapper guestMapper;


    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setGuestId(createReservationRequestDTO.getGuestId());
        ScheduleDTO scheduleDTO = scheduleRepository.findById(createReservationRequestDTO.getScheduleId())
                .map(scheduleMapper::map).<EntityNotFoundException>orElseThrow(() -> {
                    throw new EntityNotFoundException("Schedule not found.");
                });
        reservationDTO.setSchedule(scheduleDTO);
        reservationDTO.setScheduledId(createReservationRequestDTO.getScheduleId());
        KiranjyothiPRDTO guest = guestRepository.findById(createReservationRequestDTO.getGuestId())
                .map(guestMapper::map).<EntityNotFoundException>orElseThrow(() -> {
                    throw new EntityNotFoundException("Guest not found.");
                });
        reservationDTO.setGuest(guest);
        reservationDTO.setReservationStatus(ReservationStatus.READY_TO_PLAY.toString());
        reservationDTO.setValue(new BigDecimal(10));

        Reservation reservation = reservationRepository.saveAndFlush(reservationMapper.map(reservationDTO));

        Schedule schedule = scheduleMapper.map(scheduleDTO);
        schedule.addReservation(reservation);
        scheduleRepository.save(schedule);

        return reservationMapper.map(reservation);
    }

    public ReservationDTO findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservationMapper::map).<EntityNotFoundException>orElseThrow(() -> {
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

        }).<EntityNotFoundException>orElseThrow(() -> {
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
        BigDecimal refundValue = new BigDecimal("0.0");

        LocalTime time = LocalTime.of(reservation.getSchedule().getStartDateTime().getHour(),
                reservation.getSchedule().getStartDateTime().getMinute());

        BiPredicate<LocalTime, LocalTime> timePredicate = (t1, t2) -> time.isAfter(t1) && time.isBefore(t2);

        if (hours >= 24) {
            refundValue = reservation.getValue();
        } else if (timePredicate.test(LocalTime.of(12, 0), LocalTime.of(23, 59))) {
            refundValue = calculateRefundValue(reservation.getValue(), "25.00");
        } else if (timePredicate.test(LocalTime.of(2, 0), LocalTime.of(11, 59))) {
            refundValue = calculateRefundValue(reservation.getValue(), "50.00");
        } else if (timePredicate.test(LocalTime.of(0, 1), LocalTime.of(2, 0))) {
            refundValue = calculateRefundValue(reservation.getValue(), "75.00");
        }

        return refundValue;
    }

    private BigDecimal calculateRefundValue(BigDecimal value, String percentage) {
        return value.multiply(new BigDecimal(percentage)).divide(new BigDecimal("100.00"), 2,
                RoundingMode.CEILING);
    }

    /*TODO: This method actually not fully working, find a way to fix the issue when it's throwing the error:
            "Cannot reschedule to the same slot.*/
    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = cancel(previousReservationId);

        if (scheduleId.equals(previousReservation.getSchedule().getId())) {
            previousReservation.setReservationStatus(ReservationStatus.READY_TO_PLAY);
            previousReservation.setValue(previousReservation.getRefundValue());
            previousReservation.setRefundValue(new BigDecimal("0.00"));
            return reservationMapper.map(reservationRepository.save(previousReservation));
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
