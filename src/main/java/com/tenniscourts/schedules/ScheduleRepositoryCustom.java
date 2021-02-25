package com.tenniscourts.schedules;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepositoryCustom {
    List<Schedule> findSchedulesWithReservationsDifferentThanReadyToPlayByScheduleDate(LocalDate scheduleDate);

    List<Schedule> findSchedulesWithNoReservationsByScheduleDate(LocalDate scheduleDate);
}
