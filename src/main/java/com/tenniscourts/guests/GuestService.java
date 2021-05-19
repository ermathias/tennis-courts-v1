package com.tenniscourts.guests;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.reservations.CreateReservationRequestDTO;
import com.tenniscourts.reservations.Reservation;
import com.tenniscourts.reservations.ReservationDTO;
import com.tenniscourts.reservations.ReservationStatus;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GuestService {
//private final GuestRepository /guestRepository;
	
//    private final GuestMapper guestMapper;

//    public ReservationDTO createGuest(CreateReservationRequestDTO createReservationRequestDTO) throws Exception {
//        throw new UnsupportedOperationException();
//    }
//
//    public ReservationDTO findGuestbyId(Long reservationId) throws Exception{
//        return guestRepository.findById(reservationId).map(guestMapper::map).orElseThrow(() -> {
//            throw new EntityNotFoundException("Reservation not found.");
//        });
//    	
////    	return new ReservationDTO();
//    }
//
//    public ReservationDTO cancelReservation(Long reservationId) throws Exception{
//        return guestMapper.map(this.cancel(reservationId));
//    }
//
//    private Reservation cancel(Long reservationId) throws Exception{
//        return guestRepository.findById(reservationId).map(reservation -> {
//
//            this.validateCancellation(reservation);
//
//            BigDecimal refundValue = getRefundValue(reservation);
//            return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);
//
//        }).orElseThrow(() -> {
//            throw new EntityNotFoundException("Reservation not found.");
//        });
//    	
////    	return new Reservation();
//    }
//
//    private Reservation updateReservation(Reservation reservation, BigDecimal refundValue, ReservationStatus status) {
//        reservation.setReservationStatus(status);
//        reservation.setValue(reservation.getValue().subtract(refundValue));
//        reservation.setRefundValue(refundValue);
//
//        return guestRepository.save(reservation);
//    }
//
//    private void validateCancellation(Reservation reservation) {
//        if (!ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
//            throw new IllegalArgumentException("Cannot cancel/reschedule because it's not in ready to play status.");
//        }
//
//        if (reservation.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())) {
//            throw new IllegalArgumentException("Can cancel/reschedule only future dates.");
//        }
//    }
//
//    public BigDecimal getRefundValue(Reservation reservation) {
//        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());
//
//        if (hours >= 24) {
//            return reservation.getValue();
//        }
//
//        return BigDecimal.ZERO;
//    }
//
//    /*TODO: This method actually not fully working, find a way to fix the issue when it's throwing the error:
//            "Cannot reschedule to the same slot.*/
//    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) throws Exception{
//        Reservation previousReservation = cancel(previousReservationId);
//
//        if (scheduleId.equals(previousReservation.getSchedule().getId())) {
//            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
//        }
//
//        previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
//        guestRepository.save(previousReservation);
//
//        ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO.builder()
//                .guestId(previousReservation.getGuest().getId())
//                .scheduleId(scheduleId)
//                .build());
//        newReservation.setPreviousReservation(guestMapper.map(previousReservation));
//        return newReservation;
////    	return new ReservationDTO();
//    }
}
