package com.tenniscourts.reservations;

import com.tenniscourts.guests.FixtureGuest;
import com.tenniscourts.schedules.FixtureSchedules;
import com.tenniscourts.schedules.Schedule;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class FixtureReservations {

    public static Reservation fixtureReservation(){
        var reservation = new Reservation();
        reservation.setId(1L);
        return reservation;
    }

    public static ReservationDTO fixtureReservationDTO(){
        var reservationDTO = new ReservationDTO();
        reservationDTO.setId(1L);
        return reservationDTO;
    }

    public static Reservation fixtureReservationCancellation(){
        var schedule= FixtureSchedules.fixtureScheduleReservation(1L);

        var reservation = new Reservation();
        reservation.setId(1L);
        reservation.setSchedule(schedule);
        reservation.setValue(new BigDecimal(10L));

        return reservation;
    }

    public static Reservation fixtureReservationWithStatusCancelled(){
        var reservation = new Reservation();
        reservation.setReservationStatus(ReservationStatus.CANCELLED);

        return reservation;
    }

    public static Reservation fixtureReservationWithExpiredSchedule(){
        var reservation = new Reservation();
        reservation.setReservationStatus(ReservationStatus.READY_TO_PLAY);
        Schedule schedule = new Schedule();
        LocalDateTime startDateTime = LocalDateTime.now().minusDays(2);
        schedule.setStartDateTime(startDateTime);
        reservation.setSchedule(schedule);

        return reservation;
    }

    public static Reservation fixtureReservationWithSchedules(Schedule schedule){
        var guest = FixtureGuest.fixtureGuest();
        var reservation = new Reservation();
        reservation.setId(1L);
        reservation.setSchedule(schedule);
        reservation.setGuest(guest);
        reservation.setValue(new BigDecimal("100"));

        return reservation;
    }

    public static Reservation fixtureReservationWithSchedulesAndValue(){
        var guest = FixtureGuest.fixtureGuest();
        var schedule = FixtureSchedules.fixtureScheduleReservation(1L);
        var reservation = new Reservation();
        reservation.setId(1L);
        reservation.setSchedule(schedule);
        reservation.setGuest(guest);
        reservation.setValue(new BigDecimal("100"));

        return reservation;
    }

    public static List<Reservation> fixtureReservationList(){
        var firstReservation = fixtureReservation();
        var secondReservation = fixtureReservation();

        return Arrays.asList(firstReservation, secondReservation);
    }

    public static List<ReservationDTO> fixtureReservationDTOList(){
        var firstReservationDTO = fixtureReservationDTO();
        var secondReservationDTO = fixtureReservationDTO();

        return Arrays.asList(firstReservationDTO, secondReservationDTO);
    }


}
