package com.tenniscourts.schedules;

import java.time.LocalDate;
import java.util.List;

import com.tenniscourts.reservations.ReservationStatus;

public interface ScheduleRepositoryCustom {
    List<Schedule> findSchedulesWithReservationsByReservationStatusList(LocalDate scheduleDate, ReservationStatus... reservationStatusList);

    List<Schedule> findSchedulesWithNoReservationsByScheduleDate(LocalDate scheduleDate);
}
