package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.schedules.ScheduleRepository;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {

    @Autowired
    private final ReservationRepository reservationRepository;
    @Autowired
    private final ReservationMapper reservationMapper;

    @Autowired
    private final ScheduleRepository scheduleRepository;

    @Autowired
    private final GuestRepository guestRepository;

    @Transactional(rollbackFor = Exception.class)
    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        Reservation reservation = Reservation.builder()
                .schedule(scheduleRepository.findById(createReservationRequestDTO.getScheduleId())
                        .orElseThrow(() -> new EntityNotFoundException("Schedule not found.")))
                .guest(guestRepository.findById(createReservationRequestDTO.getGuestId())
                        .orElseThrow(() -> new EntityNotFoundException("Guest not found.")))
                .reservationStatus(ReservationStatus.READY_TO_PLAY).value(BigDecimal.valueOf(10))
                .refundValue(BigDecimal.valueOf(10)).build();
        return reservationMapper.map(reservationRepository.save(reservation));
    }

    public List<ReservationDTO> findAll() {
        return reservationMapper.map(reservationRepository.findAll());
    }

    public ReservationDTO findReservationById(Long id) {
        return reservationRepository.findById(id).map(reservationMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found."));
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        return reservationMapper.map(this.cancel(reservationId));
    }

    private Reservation cancel(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {
            this.validateCancellation(reservation);
            BigDecimal refundValue = getRefundValue(reservation);
            return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);
        }).orElseThrow(() -> new EntityNotFoundException("Reservation not found."));
    }

    private void validateCancellation(Reservation reservation) {
        if (!ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
            throw new IllegalArgumentException("Cannot cancel/reschedule because it's not in ready to play status.");
        }
        if (reservation.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Can cancel/reschedule only future dates.");
        }
    }

    public ReservationDTO missedReservation(Long reservationId) {
        return reservationMapper.map(this.missed(reservationId));
    }

    private Reservation missed(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {
            this.validateNoShow(reservation);
            BigDecimal refundValue = BigDecimal.ZERO;
            return this.updateReservation(reservation, refundValue, ReservationStatus.MISSED);
        }).orElseThrow(() -> new EntityNotFoundException("Reservation not found."));
    }

    private void validateNoShow(Reservation reservation) {
        if (!ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
            throw new IllegalArgumentException("Cannot change because it's not in ready to play status.");
        }
        if (reservation.getSchedule().getStartDateTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Can change only past dates.");
        }
    }

    public ReservationDTO concludedReservation(Long reservationId) {
        return reservationMapper.map(this.concluded(reservationId));
    }

    private Reservation concluded(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {
            this.validateConcluded(reservation);
            BigDecimal refundValue = reservation.getValue();
            return this.updateReservation(reservation, refundValue, ReservationStatus.CONCLUDED);
        }).orElseThrow(() -> new EntityNotFoundException("Reservation not found."));
    }

    private void validateConcluded(Reservation reservation) {
        if (!ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
            throw new IllegalArgumentException("Cannot change because it's not in ready to play status.");
        }
        if (reservation.getSchedule().getStartDateTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Can change only past dates.");
        }
    }

    private Reservation updateReservation(Reservation reservation, BigDecimal refundValue, ReservationStatus status) {
        reservation.setReservationStatus(status);
        reservation.setValue(reservation.getValue().subtract(refundValue));
        reservation.setRefundValue(refundValue);
        return reservationRepository.save(reservation);
    }

    public BigDecimal getRefundValue(Reservation reservation) {
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());
        if (hours >= 24) {
            return reservation.getValue();
        } else if (hours <= 2) {
            return reservation.getValue().multiply(new BigDecimal(0.25));
        } else if (hours < 12) {
            return reservation.getValue().multiply(new BigDecimal(0.5));
        } else if (hours < 24) {
            return reservation.getValue().multiply(new BigDecimal(0.75));
        }
        return BigDecimal.ZERO;
    }

    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = cancel(previousReservationId);
        if (scheduleId.equals(previousReservation.getSchedule().getId())) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }
        previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
        reservationRepository.save(previousReservation);
        ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO.builder()
                .guestId(previousReservation.getGuest().getId()).value(previousReservation.getValue()).scheduleId(scheduleId).build());
        newReservation.setPreviousReservation(reservationMapper.map(previousReservation));
        return newReservation;
    }
}
