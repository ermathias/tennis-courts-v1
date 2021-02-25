package com.tenniscourts.schedules;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends ScheduleRepositoryCustom, JpaRepository<Schedule, Long> {

    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);

    List<Schedule> findAllByStartDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Schedule> findAllByStartDateTimeAndEndDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime);
}