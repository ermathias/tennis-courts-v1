package com.tenniscourts.service;

import com.tenniscourts.dto.CreateReservationRequestDTO;
import com.tenniscourts.dto.ReservationDTO;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.mapper.ReservationMapper;
import com.tenniscourts.model.Reservation;
import com.tenniscourts.repository.ReservationRepository;
import com.tenniscourts.model.ReservationStatus;
import com.tenniscourts.util.Consts;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    private final GuestService guestService;

    private final ScheduleService scheduleService;

    private int reservationDeposit;

    private int reservationFee;

     public  ReservationService(final ReservationRepository reservationRepository,
                                final ReservationMapper reservationMapper,
                                final GuestService guestService,
                                final ScheduleService scheduleService,
                                final @Value("${reservation.deposit}") int reservationDeposit,
                                final @Value("${reservation.fee}") int reservationFee) {
         this.reservationMapper = reservationMapper;
         this.reservationRepository =reservationRepository;
         this.guestService = guestService;
         this.scheduleService = scheduleService;
         this.reservationDeposit = reservationDeposit;
         this.reservationFee = reservationFee;
     }



        public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {

        Reservation reservation = this.reservationMapper.map(createReservationRequestDTO);

        BigDecimal deposit = new BigDecimal(reservationDeposit);

        BigDecimal fee = new BigDecimal(reservationFee);

        BigDecimal value = fee.add(deposit);

        reservation.setValue(value);

        return reservationMapper.map(reservationRepository.save(reservation));

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

        if (hours >= Consts.ONE_DAY) {
            return reservation.getValue();
        }

        return BigDecimal.ZERO;
    }

    /*TODO: This method actually not fully working, find a way to fix the issue when it's throwing the error:
            "Cannot reschedule to the same slot.*/
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
