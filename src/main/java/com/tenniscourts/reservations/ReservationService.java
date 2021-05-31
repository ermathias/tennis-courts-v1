package com.tenniscourts.reservations;

import static com.tenniscourts.reservations.ReservationStatus.READY_TO_PLAY;
import static com.tenniscourts.utils.TennisCourtsConstraints.NOT_INFORMED_GUEST;
import static com.tenniscourts.utils.TennisCourtsConstraints.NOT_INFORMED_RESERVATION;
import static com.tenniscourts.utils.TennisCourtsConstraints.NOT_INFORMED_SCHEDULE;
import static java.math.BigDecimal.TEN;
import static java.util.Objects.isNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenniscourts.exceptions.EntityNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ReservationDTO bookReservation(CreateReservationRequestDTO dto) {
        this.validateCreation(dto);
        
        if(!isScheduleAvailable(dto.getScheduleId())) 
        	throw new IllegalArgumentException("Schedule is already taken.");
        
        Reservation reservation = reservationMapper.map(dto);
    	reservation.setValue(TEN);
    	reservationRepository.save(reservation);
        return reservationMapper.map(reservation);
        
    }
    
    private boolean isScheduleAvailable(Long scheduleId){
    	List<Reservation> reservations = reservationRepository.findBySchedule_Id(scheduleId);
    	return reservations.stream().noneMatch( reservation -> reservation.getReservationStatus() == READY_TO_PLAY);
    }

    private void validateCreation(CreateReservationRequestDTO dto) {
		if(isNull(dto))
			throw new IllegalArgumentException(NOT_INFORMED_RESERVATION);
		
		if(isNull(dto.getGuestId()))
			throw new IllegalArgumentException(NOT_INFORMED_GUEST);
		
		if(isNull(dto.getScheduleId()))
			throw new IllegalArgumentException(NOT_INFORMED_SCHEDULE);
	}

	public ReservationDTO findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservationMapper::map).<EntityNotFoundException>orElseThrow(() -> {
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

        }).<EntityNotFoundException>orElseThrow(() -> {
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
        }

        return BigDecimal.ZERO;
    }

    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
    	
    	ReservationDTO reservationDTO = this.findReservation(previousReservationId);

        if (scheduleId.equals(reservationDTO.getSchedule().getId())) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }
        
        Reservation previousReservation = cancel(previousReservationId);

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
