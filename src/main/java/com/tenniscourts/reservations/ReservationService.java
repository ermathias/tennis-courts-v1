package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleRepository;
import com.tenniscourts.tenniscourts.TennisCourt;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    private final GuestRepository guestRepository;

    private final ScheduleRepository scheduleRepository;

    public List<ReservationDTO> bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        Guest bookingGuest = guestRepository.findById(createReservationRequestDTO.getGuestId()).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
        List<Schedule> bookingSchedules = scheduleRepository.findAllById(createReservationRequestDTO.getScheduleIds());
        if (bookingSchedules.size() == 0 || bookingSchedules.size() != createReservationRequestDTO.getScheduleIds().size()) {
            throw new IllegalArgumentException("Invalid schedule ids.");
        }

        checkForDuplicateTennisCourts(bookingSchedules);

        List<Reservation> reservations = new ArrayList<>();
        bookingSchedules.forEach(schedule -> {
            Reservation reservation = new Reservation();
            reservation.setGuest(bookingGuest);
            reservation.setSchedule(schedule);
            reservation.setValue(BigDecimal.TEN);
            reservation.setReservationStatus(ReservationStatus.READY_TO_PLAY);
            reservations.add(reservation);
        });
        return reservationMapper.map(reservationRepository.saveAll(reservations));
    }

    public ReservationDTO findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservationMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found");
        });
        return reservationMapper.map(this.cancel(reservation));
    }

    private Reservation cancel(Reservation reservation) {
        this.validateCancellation(reservation);
        BigDecimal refundValue = getRefundValue(reservation);
        return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);
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
        } else if (hours >= 12) {
            return reservation.getValue().divide(BigDecimal.valueOf(4), 2, RoundingMode.CEILING).multiply(BigDecimal.valueOf(3));
        } else if (hours >= 2) {
            return reservation.getValue().divide(BigDecimal.valueOf(2), 2, RoundingMode.CEILING);
        } else {
            return reservation.getValue().divide(BigDecimal.valueOf(4), 2, RoundingMode.CEILING);
        }
    }

    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = reservationRepository.findById(previousReservationId).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });

        if (scheduleId.equals(previousReservation.getSchedule().getId())) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }
        previousReservation = cancel(previousReservation);

        previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
        reservationRepository.save(previousReservation);

        CreateReservationRequestDTO newReservationRequestDTO = new CreateReservationRequestDTO();
        List<Long> scheduleIds = new ArrayList<>();
        scheduleIds.add(scheduleId);
        newReservationRequestDTO.setGuestId(previousReservation.getGuest().getId());
        newReservationRequestDTO.setScheduleIds(scheduleIds);
        ReservationDTO newReservation = bookReservation(newReservationRequestDTO).get(0);

        newReservation.setPreviousReservation(reservationMapper.map(previousReservation));
        return newReservation;
    }

    private void checkForDuplicateTennisCourts(List<Schedule> bookingSchedules) {
        Set<TennisCourt> set = new HashSet<>();
        if (!bookingSchedules.stream().allMatch(schedule -> set.add(schedule.getTennisCourt()))) {
            throw new IllegalArgumentException("Multiple schedules cannot have the same tennis court.");
        }
    }
}
