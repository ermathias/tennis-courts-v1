package com.tenniscourts.schedules;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);

	List<Schedule> findByStartDateTimeBetweenOrEndDateTimeBetween(LocalDateTime startDate1, LocalDateTime endDate1,
			LocalDateTime startDate2, LocalDateTime endDate2);
}