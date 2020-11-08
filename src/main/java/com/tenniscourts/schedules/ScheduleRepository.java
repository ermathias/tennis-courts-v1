package com.tenniscourts.schedules;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);

    List<Schedule> findAllByStartDateTimeLessThanEqualAndEndDateTimeGreaterThanEqual(LocalDateTime endDate,
            LocalDateTime startDate);

    List<Schedule> findAllByStartDateTimeGreaterThanEqual(LocalDateTime startDate);
}