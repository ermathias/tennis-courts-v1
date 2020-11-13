package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final GuestRepository guestRepository;

    private final ScheduleRepository scheduleRepository;

    private final ReservationMapper reservationMapper;


    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        Guest guest = guestRepository.getOne(createReservationRequestDTO.getGuestId());
        Schedule schedule = scheduleRepository.getOne(createReservationRequestDTO.getScheduleId());

        if(Objects.nonNull(guest.getId()) && Objects.nonNull(schedule.getId())){
            /**
             * All reservation fees are 100.00, and each reservation has 10.00 reservation deposit;
             * tirar depois...
             */
            Reservation reservation = new Reservation(guest, schedule,  new BigDecimal("100.00").add(new BigDecimal("10.00")) , ReservationStatus.READY_TO_PLAY, new BigDecimal("0.00") );
            schedule.addReservation(reservation);
            return reservationMapper.map(reservationRepository.saveAndFlush(reservation));

        }
        return null;
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
        BigDecimal refundValue = reservation.getValue();
        if (hours >= 24) {
            return reservation.getValue();
        } else if (hours >= 12 && hours <= 23){
            return refundValue = refundValue.divide(new BigDecimal("4")).multiply(new BigDecimal("3"));

        }else if (hours >= 2 && hours <= 11){
            return refundValue = refundValue.divide(new BigDecimal("4")).multiply(new BigDecimal("2"));
        }else if (hours >= 0  && hours <= 2){
            return refundValue = refundValue.divide(new BigDecimal("4"));
        }

        return BigDecimal.ZERO;
    }

    /*TODO: This method actually not fully working, find a way to fix the issue when it's throwing the error:
            "Cannot reschedule to the same slot.*/
    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = cancel(previousReservationId);

        if (scheduleId.equals(previousReservation.getSchedule().getId())) {
            /**
             * ReservationStatus should not remain canceled after the error below is thrown.
             * */
            previousReservation.setReservationStatus(ReservationStatus.READY_TO_PLAY);
            reservationRepository.save(previousReservation);
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

    public List<Reservation> findReservationByScheduleId(Long scheduleId){
        return reservationRepository.findBySchedule_Id(scheduleId);
    }

    public List<ReservationDTO> getAllMyReservations(Long id){
        Guest userAction = guestRepository.getOne(id);
        if(userAction.isAdmin()){
            List<Reservation> myReservations = reservationRepository.findByGuest_IdAndSchedule_StartDateTimeLessThan(id, LocalDateTime.now());
            return convertToDto(myReservations);
        }
        throw new UnsupportedOperationException("Only admin users are allowed to do that.");
    }

    private List<ReservationDTO> convertToDto(List<Reservation> reservations){
        List<ReservationDTO> list = new ArrayList<>();
        for(Reservation temp : reservations){
            list.add(reservationMapper.map(temp));
        }
       return list;
    }

}
