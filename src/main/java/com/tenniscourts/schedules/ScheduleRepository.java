package com.tenniscourts.schedules;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);

    List<Schedule> findByStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqual(LocalDateTime startDateTime, LocalDateTime endDateTime);
}