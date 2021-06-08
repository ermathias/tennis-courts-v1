package com.tenniscourts.reservations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    
    private final ScheduleService scheduleService;

    private final ReservationMapper reservationMapper;

    //1. As a User I want to be able to book a reservation for one or more tennis court at a given date schedule
    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
    	Schedule schedule = scheduleService.findById(createReservationRequestDTO.getScheduleId());
    	return reservationMapper.map(reservationRepository.saveAndFlush(reservationMapper.map(createReservationRequestDTO, schedule)));
    }

    public ReservationDTO findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservationMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    //3. As a User I want to be able to cancel a reservation
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

    //8. As a Tennis Court Admin, I want to refund the reservation deposit if the user has cancelled or rescheduled their reservation more than 24 hours in advance
    
    /*9. As a Tennis Court Admin, I want to keep 25% of the reservation fee if the User cancels or reschedules between 
    	12:00 and 23:59 hours in advance, 50% between 2:00 and 11:59 in advance, and 75% between 0:01 and 2:00 in advance*/
    public BigDecimal getRefundValue(Reservation reservation) {
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());

        if (hours >= 24) {
            return reservation.getValue();
        } else if (hours < 24 && hours >= 12) {
        	BigDecimal refundValue = new BigDecimal(reservation.getValue().doubleValue() * 0.75);
        	return refundValue;
        } else if (hours < 12 && hours >= 2) {
        	BigDecimal refundValue = new BigDecimal(reservation.getValue().doubleValue() * 0.5);
        	return refundValue;
        }  else if (hours < 2 && hours >= 0) {
        	BigDecimal refundValue = new BigDecimal(reservation.getValue().doubleValue() * 0.25);
        	return refundValue;
        }

        return BigDecimal.ZERO;
    }
    
    //11. As a Tennis Court Admin, I want to be able to see a history of my past reservations so that I can use the information to improve the management of my establishment
    public Page<ReservationDTO> findAll(Integer page, Integer linesPerPage, String orderBy, String direction) {
    	PageRequest pageable = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    	Page<Reservation> reservations = reservationRepository.findAll(pageable);
    	return reservations.map(reservation -> reservationMapper.map(reservation));
    }

    /*TODO: This method actually not fully working, find a way to fix the issue when it's throwing the error:
            "Cannot reschedule to the same slot.*/
    //4. As a User I want to be able to reschedule a reservation
    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = cancel(previousReservationId);

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
}
