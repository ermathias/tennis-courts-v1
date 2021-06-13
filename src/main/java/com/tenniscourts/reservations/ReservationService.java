package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    private final ScheduleRepository scheduleRepository;

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        Schedule schedule = scheduleRepository
                .findById(createReservationRequestDTO.getScheduleId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Schedule with id %s not found",
                        createReservationRequestDTO.getScheduleId())));

        // since every reservation takes one hour, if a schedule contains that number of reservations, it is full.
        if (isFull(schedule)) {
            throw new IllegalStateException(String.format("Schedule with id %s is already full of reservations",
                    createReservationRequestDTO.getScheduleId()));
        }

        Reservation reservation = reservationMapper.map(createReservationRequestDTO);

        reservation.setValue(BigDecimal.valueOf(10));
        reservationRepository.save(reservation);

        schedule.addReservation(reservation);
        scheduleRepository.save(schedule);

        return reservationMapper.map(reservation);
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

        if (hours >= 24) {
            return reservation.getValue();
        }

        return BigDecimal.ZERO;
    }

    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = cancel(previousReservationId);

        if (scheduleId.equals(previousReservation.getSchedule().getId())) {

            previousReservation.setReservationStatus(ReservationStatus.READY_TO_PLAY);
            previousReservation.setValue(previousReservation.getRefundValue());

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

    private boolean isFull(Schedule schedule) {
        boolean result = false;
        long playableHours = ChronoUnit.HOURS.between(schedule.getStartDateTime(), schedule.getEndDateTime());
        if (schedule.getReservations().size() == playableHours && schedule.getReservations()
                .stream()
                .allMatch(a -> a.getReservationStatus() == ReservationStatus.READY_TO_PLAY)) {
            result = true;
        }

        return result;
    }
}
