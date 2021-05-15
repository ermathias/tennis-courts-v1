package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.schedules.ScheduleRepository;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {
	@Autowired
    private final ReservationRepository reservationRepository;
	
    @Autowired
    private final ReservationMapper reservationMapper;
    
    @Autowired
    private final GuestRepository guestRepository;

    @Autowired
    private final ScheduleRepository scheduleRepository;

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        if(scheduleRepository.findById(createReservationRequestDTO.getScheduleId()).get().getStartDateTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Can book only future dates.");
        }

        Reservation newReservation = Reservation.builder()
            .guest(guestRepository.findById(createReservationRequestDTO.getGuestId()).orElseThrow(() -> {
                throw new EntityNotFoundException("Guest not Found");
            }))
            .schedule(scheduleRepository.findById(createReservationRequestDTO.getScheduleId()).orElseThrow(() -> {
                throw new EntityNotFoundException("Schedule not Found");
            }))
            .build();
        return reservationMapper.map(reservationRepository.saveAndFlush(newReservation));
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
        } else if (hours >= 12) {
            return reservation.getValue().multiply(BigDecimal.valueOf(0.25));
        } else if (hours >= 2) {
            return reservation.getValue().multiply(BigDecimal.valueOf(0.5));
        } else if (hours >= 0) {
            return reservation.getValue().multiply(BigDecimal.valueOf(0.75));
        } else return BigDecimal.ZERO;
    }

    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = reservationRepository.findById(previousReservationId).orElseThrow(() -> {
            throw new IllegalArgumentException("Previous Reservation Not Found");
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

    public List<ReservationDTO> findAllPastReservations() {
        List<Long> scheduleIds = new ArrayList<>(); 
        scheduleIds.add(0L);
        scheduleRepository.findByEndDateTimeBefore(LocalDateTime.now()).forEach(schedule -> scheduleIds.add(schedule.getId()));
        return reservationMapper.map(reservationRepository.findByScheduleIdIn(scheduleIds));
    }
}
