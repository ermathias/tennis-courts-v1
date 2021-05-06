package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleMapper;
import com.tenniscourts.schedules.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationService {

    @Autowired
    private final ReservationRepository reservationRepository;

    @Autowired
    private final ScheduleRepository scheduleRepository;

    private final ReservationMapper reservationMapper;

    private final ScheduleMapper scheduleMapper;


    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
       return reservationMapper.map(reservationRepository.saveAndFlush(reservationMapper.map(createReservationRequestDTO)));
    }

    public ReservationDTO findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservationMapper::map)
                .orElseThrow(()-> new EntityNotFoundException(("Reservation not found.")));
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        return reservationMapper.map(this.cancel(reservationId));
    }

    private Reservation cancel(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {

            this.validateCancellation(reservation);

            BigDecimal refundValue = getRefundValue(reservation);
            return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);

        }).orElseThrow(() ->
             new EntityNotFoundException("Reservation not found.")
        );
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
            return getPercentageValue(reservation.getValue(), 100);
        }else if(hours < 24 && hours >= 12){
            return getPercentageValue(reservation.getValue(), 75);
        }else if (hours < 12 && hours >= 2){
            return getPercentageValue(reservation.getValue(), 50);
        }
        else if (hours < 2 && hours >0){
            return getPercentageValue(reservation.getValue(), 25);
        }

        return BigDecimal.ZERO;
    }
	
	public BigDecimal getPercentageValue(BigDecimal value, int percentage){
        return value.multiply(new BigDecimal(percentage)).divide(new BigDecimal(100));
    }

    /*TODO: This method actually not fully working, find a way to fix the issue when it's throwing the error:
            "Cannot reschedule to the same slot.*/
public ReservationDTO rescheduleReservation(RescheduleReservationRequestDTO rescheduleReservationRequestDTO) {
        Reservation previousReservation = cancel(rescheduleReservationRequestDTO.getReservationId());
		
 if (rescheduleReservationRequestDTO.getNewScheduleId().equals(previousReservation.getSchedule().getId())) {
	 
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }
        previousReservation.setValue(rescheduleReservationRequestDTO.getValue());
        reservationRepository.save(previousReservation);

        ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO.builder()
                .guestId(previousReservation.getGuest().getId())
               .scheduleId(rescheduleReservationRequestDTO.getNewScheduleId())
                .build());
        newReservation.setPreviousReservation(reservationMapper.map(previousReservation));
        return newReservation;
    }
}
