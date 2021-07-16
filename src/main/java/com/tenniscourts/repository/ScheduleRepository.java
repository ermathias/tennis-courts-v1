package com.tenniscourts.repository;

import com.tenniscourts.model.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByTennisCourtIdOrderByStartDateTime(Long id);

    List<Schedule> findSchedulesByStartDateTimeAndEndDateTime(LocalDateTime startDate, LocalDateTime endDate);
}