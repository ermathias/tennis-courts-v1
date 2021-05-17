package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestMapper;
import com.tenniscourts.guests.GuestService;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleMapper;
import com.tenniscourts.schedules.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    private final GuestService guestService;

    private final ScheduleService scheduleService;

    private final GuestMapper guestMapper;

    private final ScheduleMapper scheduleMapper;
    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {

        BigDecimal reservationAmt =  new BigDecimal(10);
        Guest guest = guestMapper.map(guestService.findUser(createReservationRequestDTO.getGuestId()));
        Schedule schedule = scheduleMapper.map(scheduleService.findSchedule(createReservationRequestDTO.getScheduleId()));
        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setSchedule(schedule);
        reservation.setValue(reservationAmt);
        reservation.setRefundValue(reservationAmt);
        return reservationMapper.map(reservationRepository.saveAndFlush(reservation));

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

        BigDecimal reservationPercent = null;
        BigDecimal reservationValue = reservation.getValue();
        if (hours >= 24) {
            return reservationValue;
        } else if (hours >= 12) {
            reservationPercent = new BigDecimal(0.25);
        } else if (hours >= 2) {
            reservationPercent = new BigDecimal(0.5);
        } else if (hours >= 0) {
            reservationPercent = new BigDecimal(0.75);
        } else {
            return BigDecimal.ZERO;
        }

        return reservationValue.multiply(reservationPercent);
    }

    /*TODO: This method actually not fully working, find a way to fix the issue when it's throwing the error:
            "Cannot reschedule to the same slot.*/
    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {

        Reservation previousReservation = reservationRepository.findById(previousReservationId).orElseThrow(() -> {
            throw new IllegalArgumentException("Reservation Not Exists");
        });
        cancel(previousReservationId);
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

    public List<ReservationDTO> findAllReservation() {
        return reservationMapper.map(reservationRepository.findAll());
    }
}
