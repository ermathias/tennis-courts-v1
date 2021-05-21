package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestsRepository;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final GuestsRepository guestsRepository;
    private final ScheduleRepository scheduleRepository;
    private final ReservationMapper reservationMapper;

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        return bookReservation(
                guestsRepository.getOne(createReservationRequestDTO.getGuestId()),
                scheduleRepository.getOne(createReservationRequestDTO.getScheduleId())
        );
    }

    public List<ReservationDTO> findReservations(boolean pastOnly) {
        List<Reservation> reservations;
        if (pastOnly) {
            reservations = reservationRepository.findAllByScheduleEndDateTimeBefore(LocalDateTime.now());
        } else {
            reservations = reservationRepository.findAll();
        }
        return reservations.stream().map(reservationMapper::map).collect(Collectors.toList());
    }

    public ReservationDTO findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservationMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found."));
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found."));
        reservation.update(ReservationStatus.CANCELLED);
        Reservation updated = reservationRepository.save(reservation);
        return reservationMapper.map(updated);
    }

    @Transactional
    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = findAndReschedulePreviousReservation(previousReservationId, scheduleId);
        ReservationDTO newReservation = bookReservation(previousReservation.getGuest(), scheduleRepository.getOne(scheduleId));
        newReservation.setPreviousReservation(reservationMapper.map(previousReservation));
        return newReservation;
    }

    private Reservation findAndReschedulePreviousReservation(Long previousReservationId, Long newScheduleId) {
        Reservation previousReservation = reservationRepository.findById(previousReservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found."));
        if (newScheduleId.equals(previousReservation.getSchedule().getId())) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }
        previousReservation.update(ReservationStatus.RESCHEDULED);
        return reservationRepository.save(previousReservation);
    }

    private ReservationDTO bookReservation(Guest guest, Schedule schedule) {
        if (schedule.getReservations().stream()
                .anyMatch(s -> ReservationStatus.READY_TO_PLAY == s.getReservationStatus())) {
            throw new IllegalStateException("Cannot book an already reserved schedule.");
        }
        return reservationMapper.map(reservationRepository.saveAndFlush(new Reservation(guest, schedule)));
    }
}
