package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestDTO;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleRepository;
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

    private final GuestRepository guestRepository;

    private final ScheduleRepository scheduleRepository;

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        Guest guest = guestRepository.findById(createReservationRequestDTO.getGuestId()).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });

        Schedule schedule = scheduleRepository.findById(createReservationRequestDTO.getScheduleId()).orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found.");
        });


        BigDecimal reservationMoney = new BigDecimal(10);
        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setSchedule(schedule);
        reservation.setValue(reservationMoney);
        reservation.setReservationStatus(ReservationStatus.READY_TO_PLAY);

        List<Reservation> r = schedule.getReservations();
        r.add(reservation);
        schedule.setReservations(r);
        scheduleRepository.save(schedule);

        return reservationMapper.map(reservationRepository.save(reservation));

    }

    public ReservationDTO findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservationMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    public List<ReservationDTO> findPastReservations(LocalDateTime localDateTime) {
        return reservationRepository.findAllBySchedule_StartDateTimeBefore(localDateTime).stream().map(reservationMapper::map).collect(Collectors.toList());
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        ReservationDTO r =  reservationRepository.findById(reservationId).map(reservationMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
        reservationRepository.deleteById(reservationId);
        return r;
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
        }else if (hours >= 12) {
            return reservation.getValue().multiply(BigDecimal.valueOf(0.75));
        }else if (hours >= 2) {
            return reservation.getValue().multiply(BigDecimal.valueOf(0.50));
        }else if (hours > 0) {
            return reservation.getValue().multiply(BigDecimal.valueOf(0.25));
        }else return BigDecimal.ZERO;


    }

    /*TODO: This method actually not fully working, find a way to fix the issue when it's throwing the error:
            "Cannot reschedule to the same slot.*/
    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = reservationRepository.findById(previousReservationId).orElseThrow(() -> {
            throw new EntityNotFoundException("Previous reservation not found.");
        });

        if (scheduleId.equals(previousReservation.getSchedule().getId())) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }

        cancel(previousReservationId);
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
