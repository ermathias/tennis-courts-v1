package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.BusinessException;
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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
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

    private final BigDecimal reservationFee = new BigDecimal(10);

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {

        Schedule schedule = scheduleRepository.findFirstById(createReservationRequestDTO.getScheduleId());

        if (schedule.getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Can't reserve past dates.");
        }

        List<Reservation> reservations = schedule.getReservations();

        reservations.forEach(reservation -> {
            if (reservation.getReservationStatus() == ReservationStatus.READY_TO_PLAY) {
                throw new BusinessException("Time slot is already reserved.");
            }
        });

        Guest guest = guestRepository.findFirstById(createReservationRequestDTO.getGuestId());

        if (guest.getBalance().compareTo(reservationFee) < 0) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        guest.setBalance(guest.getBalance().subtract(reservationFee));

        Reservation newReservation = reservationMapper.map(createReservationRequestDTO);
        newReservation.setReservationStatus(ReservationStatus.READY_TO_PLAY);
        newReservation.setValue(reservationFee);

        ReservationDTO newReservationDTO = reservationMapper.map(reservationRepository.save(newReservation));

        schedule.addReservation(newReservation);
        scheduleRepository.save(schedule);

        return newReservationDTO;
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

        Guest guest = reservation.getGuest();
        guest.setBalance(guest.getBalance().add(refundValue));

        guestRepository.saveAndFlush(guest);

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
        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());

        if (hours >= 24) {
            return reservation.getValue();
        }
        else if (hours >= 12 && hours < 24) {
            BigDecimal returnValue = reservation.getValue().multiply(new BigDecimal(0.75));
            return returnValue;
        }
        else if (hours >= 2 && hours < 12) {
            BigDecimal returnValue = reservation.getValue().multiply(new BigDecimal(0.5));
            return returnValue;
        }
        else if (minutes > 0 && hours < 2) {
            BigDecimal returnValue = reservation.getValue().multiply(new BigDecimal(0.25));
            return returnValue;
        }

        return BigDecimal.ZERO;
    }

    /*TODO: This method actually not fully working, find a way to fix the issue when it's throwing the error:
            "Cannot reschedule to the same slot.*/
    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = cancel(previousReservationId);

        if (scheduleId == previousReservation.getSchedule().getId()) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }

        ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO.builder()
                .guestId(previousReservation.getGuest().getId())
                .scheduleId(scheduleId)
                .build());
        previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
        reservationRepository.save(previousReservation);
        newReservation.setPreviousReservation(reservationMapper.map(previousReservation));

        return newReservation;
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation finishReservation(FinishReservationDTO finishReservationDTO) {

        Reservation reservation = reservationRepository.findFirstById(finishReservationDTO.getReservationId());

        if (reservation == null) {
            throw new EntityNotFoundException("Reservation not found.");
        }

        if (reservation.getReservationStatus() == ReservationStatus.COMPLETED) {
            throw new IllegalArgumentException("Reservation is already finished.");
        }

        if (reservation.getSchedule().getStartDateTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Can't finish reservations that haven't started yet.");
        }

        Guest guest = reservation.getGuest();

        if (finishReservationDTO.isShowedUp()) {
           BigDecimal refundValue = reservation.getValue();
           reservation.setRefundValue(refundValue);
           reservation.setValue(reservation.getValue().subtract(refundValue));
           guest.setBalance(guest.getBalance().add(refundValue));

           guestRepository.saveAndFlush(guest);
        }
        else {
            BigDecimal refundValue = new BigDecimal(0);
            reservation.setRefundValue(refundValue);
        }

        reservation.setReservationStatus(ReservationStatus.COMPLETED);

        return reservationRepository.save(reservation);
    }

    public List<Reservation> findAllPastReservations() {
        Date timeNow = new Date();
        return reservationRepository.findAllPastReservations(timeNow);
    }
}
