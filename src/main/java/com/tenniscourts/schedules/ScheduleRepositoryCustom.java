package com.tenniscourts.schedules;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepositoryCustom {
    List<Schedule> findSchedulesWithFreeTimeSlotsByScheduleDate(LocalDate scheduleDate);
}
