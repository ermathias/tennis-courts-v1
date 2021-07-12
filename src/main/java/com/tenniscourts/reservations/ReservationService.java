package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.GuestMapper;
import com.tenniscourts.guests.GuestService;
import com.tenniscourts.schedules.ScheduleMapper;
import com.tenniscourts.schedules.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    private final GuestService guestService;

    private final GuestMapper guestMapper;

    private final ScheduleService scheduleService;

    private final ScheduleMapper scheduleMapper;

    private final BigDecimal RESERVATION_FEE = BigDecimal.TEN;

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {

        Reservation bookedReservation = Reservation.builder()
                .guest(guestMapper.map(guestService.findGuestById(createReservationRequestDTO.getGuestId())))
                .schedule(scheduleMapper.map(scheduleService.findSchedule(createReservationRequestDTO.getScheduleId())))
                .reservationStatus(ReservationStatus.READY_TO_PLAY)
                .refundValue(RESERVATION_FEE)
                .value(RESERVATION_FEE)
                .build();

        return reservationMapper.map(reservationRepository.save(bookedReservation));
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
        long hoursBeforeReservation = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());
        long minutesBeforeReservation = ChronoUnit.MINUTES.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());

        BigDecimal reservationFee = reservation.getValue();

        if (hoursBeforeReservation >= 24) {
            return reservationFee;
        }

        // keep 1/4 of the deposit if cancelled/rescheduled between 12 and 23:59 hours in advance
        else if (minutesBeforeReservation >= 720 && minutesBeforeReservation <= 1439) {
            return reservationFee.multiply(new BigDecimal(0.75));
        }

        // keep 1/2 of the deposit if cancelled/rescheduled between 2 and 11:59 hours in advance
        else if (minutesBeforeReservation > 120 && minutesBeforeReservation <= 719) {
            return reservationFee.multiply(new BigDecimal(0.50));
        }

        // keep 3/4 of the deposit if cancelled/rescheduled between 1 minute and 2 hours in advance
        else if (minutesBeforeReservation >= 1 && minutesBeforeReservation < 120) {
            return reservationFee.multiply(new BigDecimal(0.25));
        }

        return BigDecimal.ZERO;
    }

    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = reservationMapper.map(findReservation(previousReservationId));

        if (scheduleId.equals(previousReservation.getSchedule().getId())) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }

        previousReservation = cancel(previousReservationId);
        previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
        reservationRepository.save(previousReservation);

        ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO.builder()
                .guestId(previousReservation.getGuest().getId())
                .scheduleId(scheduleId)
                .build());
        newReservation.setPreviousReservation(reservationMapper.map(previousReservation));
        return newReservation;
    }

    public List<ReservationDTO> findAllReservations() {
        return reservationRepository.findAll().stream().map(reservationMapper::map).collect(Collectors.toList());
    }
}
