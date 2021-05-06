package com.tenniscourts.schedules;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDateTime;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);
	
	List<Schedule> findAllByStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqual(LocalDateTime startDate, LocalDateTime endDate);
}