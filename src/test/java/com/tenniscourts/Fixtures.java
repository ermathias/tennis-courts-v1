package com.tenniscourts;

import com.tenniscourts.model.Guest;
import com.tenniscourts.model.Reservation;
import com.tenniscourts.model.Schedule;
import com.tenniscourts.model.TennisCourt;
import com.tenniscourts.storage.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class Fixtures {

    public static com.tenniscourts.model.Guest buildGuest() {
        return Guest.builder()
                .name("Paul")
                .build();
    }

    public static com.tenniscourts.storage.CreateGuestDTO buildCreateGuestDTO() {
        return CreateGuestDTO.builder()
                .name("Paul Dirac")
                .build();
    }

    public static com.tenniscourts.model.Schedule buildSchedule() {
        return Schedule.builder()
                .startDateTime(LocalDateTime.now().plusDays(2))
                .endDateTime(LocalDateTime.now().plusHours(3))
                .reservations(buildListReservation())
                .tennisCourt(buildTennisCourt())
                .build();
    }

    public static com.tenniscourts.storage.ScheduleDTO buildScheduleDTO() {
        LocalDateTime startDateTime = LocalDateTime.of(2021, 2, 13, 15, 56);
        LocalDateTime endDateTime = LocalDateTime.of(2021, 2, 13, 16, 56);

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(1L);
        scheduleDTO.setStartDateTime(startDateTime);
        scheduleDTO.setEndDateTime(endDateTime);
        scheduleDTO.setTennisCourtId(1L);
        scheduleDTO.setTennisCourt(null);

        return scheduleDTO;
    }

    public static com.tenniscourts.storage.CreateScheduleRequestDTO buildCreateScheduleRequestDTO() {
        LocalDateTime startDateTime = LocalDateTime.of(2017, 2, 13, 15, 56);
        return CreateScheduleRequestDTO.builder()
                .tennisCourtId(1L)
                .startDateTime(startDateTime)
                .build();
    }

    public static List<Reservation> buildListReservation() {
        com.tenniscourts.model.Reservation reservation = com.tenniscourts.model.Reservation.builder()
                .reservationStatus(ReservationStatus.READY_TO_PLAY)
                .build();

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        return reservations;
    }

    public static TennisCourt buildTennisCourt() {
        return TennisCourt.builder()
                .name("Paul")
                .build();
    }

    public static com.tenniscourts.storage.TennisCourtDTO buildTennisCourtDTO() {
        return TennisCourtDTO.builder()
                .id(1L)
                .name("Carl Sagan")
                .tennisCourtSchedules(List.of(buildScheduleDTO()))
                .build();
    }

    public static com.tenniscourts.storage.ReservationDTO buildReservationDTO() {
        return ReservationDTO.builder()
                .id(1L)
                .previousReservation(any())
                .value(new BigDecimal(10))
                .refundValue(new BigDecimal(10))
                .reservationStatus(ReservationStatus.READY_TO_PLAY.toString())
                .guestId(1L)
                .scheduledId(1L)
                .schedule(buildScheduleDTO())
                .build();
    }

    public static com.tenniscourts.storage.CreateReservationRequestDTO buildCreateReservationRequestDTO() {
        return CreateReservationRequestDTO.builder()
                .guestId(1L)
                .scheduleId(1L)
                .build();
    }

    public static com.tenniscourts.model.Reservation buildReservation() {
        return Reservation.builder()
                .reservationStatus(ReservationStatus.CANCELLED)
                .value(new BigDecimal(10))
                .refundValue(new BigDecimal(10))
                .schedule(buildSchedule())
                .build();
    }
}
