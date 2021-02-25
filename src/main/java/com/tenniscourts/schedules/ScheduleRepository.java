package com.tenniscourts.schedules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import com.tenniscourts.tenniscourts.TennisCourt;

@Repository
public interface ScheduleRepository extends ScheduleRepositoryCustom, JpaRepository<Schedule, Long> {

    List<Schedule> findAllByTennisCourt(TennisCourt tennisCourt);

    List<Schedule> findAllByStartDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Schedule> findAllByStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqual(LocalDateTime startDateTime, LocalDateTime endDateTime);
}