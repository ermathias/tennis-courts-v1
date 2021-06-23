package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class ReservationService {

    private static final BigDecimal RESERVATION_FEE = new BigDecimal(10);

    @Autowired
    private final ReservationRepository reservationRepository;

    @Autowired
    private final GuestRepository guestRepository;

    @Autowired
    private final ReservationMapper reservationMapper;

    @Autowired
    private final ScheduleRepository scheduleRepository;


    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        Guest guest = guestRepository.findById(createReservationRequestDTO.getGuestId()).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
        Schedule schedule = scheduleRepository.findById(createReservationRequestDTO.getScheduleId()).orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found.");
        });
        Reservation reservation = Reservation.builder().reservationStatus(ReservationStatus.READY_TO_PLAY)
                .guest(guest)
                .refundValue(RESERVATION_FEE)
                .schedule(schedule)
                .value(RESERVATION_FEE).build();

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
        LocalTime reservationTime = reservation.getSchedule().getStartDateTime().toLocalTime();
        BigDecimal fee = reservation.getValue();

        if (hours >= 24) {
            return reservation.getValue();
        } else if (hours < 12 && reservationTime.isAfter(LocalTime.of(12, 0)) && reservationTime.isBefore(LocalTime.of(23, 59))) {
            fee = fee.divide(new BigDecimal(4));
        }
        if (hours < 10 && reservationTime.isAfter(LocalTime.of(2, 00)) && reservationTime.isBefore(LocalTime.of(11, 59))) {
            fee = fee.divide(new BigDecimal(2));

        }
        if (hours < 2 && reservationTime.isAfter(LocalTime.of(0, 01)) && reservationTime.isBefore(LocalTime.of(2, 00))) {
            fee = fee.multiply(new BigDecimal(0.75));

        }

        return fee;

    }


    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        //Im changing the status for the previous reservation and get the fee accordingly
        Reservation previousReservation = cancel(previousReservationId);

        //Im creating a new reservation with the status Ready to play
        ReservationDTO newReservationDTO = bookReservation(CreateReservationRequestDTO.builder()
                .guestId(previousReservation.getGuest().getId())
                .scheduleId(scheduleId)
                .build());

        //Changing the status to Rescheduled so I can have a track of the rescheduled reservations
        previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
        reservationRepository.save(previousReservation);
        //Setting the previous reservation
        newReservationDTO.setPreviousReservation(reservationMapper.map(previousReservation));

        //In the end we have 2 reservations but only the last one is active
        return newReservationDTO;
    }
}
