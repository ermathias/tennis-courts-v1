package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.GuestDTO;
import com.tenniscourts.guests.GuestMapper;
import com.tenniscourts.guests.GuestService;
import com.tenniscourts.schedules.ScheduleMapper;
import com.tenniscourts.schedules.ScheduleRepository;
import com.tenniscourts.schedules.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    private final ScheduleService scheduleService;

    private final ScheduleMapper scheduleMapper;

    private final GuestService guestService;

    private final GuestMapper guestMapper;

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
            return reservationMapper.map(bookReservationForDB(createReservationRequestDTO));

    }

    public Reservation bookReservationForDB(CreateReservationRequestDTO createReservationRequestDTO) {
        Reservation obj = new Reservation();
        GuestDTO guest = guestService.findGuestById(createReservationRequestDTO.getGuestId());
        obj.setId(null);
        obj.setReservationStatus(ReservationStatus.READY_TO_PLAY);
        obj.setValue(new BigDecimal(110.00));
        obj.setGuest(guestMapper.map(guest));
        obj.setSchedule(scheduleMapper.map(scheduleService.findSchedule(createReservationRequestDTO.getScheduleId())));
        return reservationRepository.save(obj);
    }

    public ReservationDTO findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservationMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    public ReservationDTO confirmGameCompletion(Long reservationId) {
        return reservationMapper.map(this.confirmGame(reservationId));
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        return reservationMapper.map(this.cancel(reservationId));
    }

    private Reservation cancel(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {

            this.validateCancellationOrConfirmGamePlayed(reservation, 1);

            BigDecimal refundValue = getRefundValue(reservation);
            return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);

        }).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    private Reservation confirmGame(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {

            this.validateCancellationOrConfirmGamePlayed(reservation, 2);

            BigDecimal refundValue = new BigDecimal(10);
            return this.updateReservation(reservation, refundValue, ReservationStatus.GAME_PLAYED);

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

    private void validateCancellationOrConfirmGamePlayed(Reservation reservation, int option) {
        if (!ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
            throw new IllegalArgumentException("Cannot cancel/reschedule because it's not in ready to play status.");
        }
        if(option == 1){
            if (reservation.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("Can cancel/reschedule only future dates.");
            }
        }

    }

    public BigDecimal getRefundValue(Reservation reservation) {
        long hours = ChronoUnit.MINUTES.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());
        BigDecimal value = reservation.getValue();

        //25% 12:00 and 23:59 hours in advance
        if(hours >= 12 * 60 && hours < 24 * 60){
            return  generateRefundCharge(value, 0.75);
        }
        // 50% between 2:00 and 11:59 in advance
        if(hours >= 2 * 60 && hours < 12 * 60){
            return  generateRefundCharge(value, 0.5);
        }
        // and 75% between 0:01 and 2:00 in advance
        if(hours > 0 && hours < 2 * 60){
            return  generateRefundCharge(value, 0.25);
        }

        if (hours >= 24 * 60) {
            return reservation.getValue();
        }

        return BigDecimal.ZERO;
    }

    //TASK DONE user can insert the same Schedule without causing error in the Reservation register
    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = reservationMapper.map(findReservation(previousReservationId));

        if (scheduleId.equals(previousReservation.getSchedule().getId())) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }

        previousReservation = cancel(previousReservationId);
        previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
        reservationRepository.save(previousReservation);

        ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO.builder()
                .guestId(previousReservation.getGuest().getId())
                .scheduleId(scheduleId)
                .build());
        newReservation.setPreviousReservation(reservationMapper.map(previousReservation));
        return newReservation;
    }

    private BigDecimal generateRefundCharge(BigDecimal value, Double percentage){
        return value.subtract(new BigDecimal(10))
                .multiply(new BigDecimal(percentage))
                .add(new BigDecimal(10))
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}
