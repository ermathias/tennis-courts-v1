package com.tenniscourts.schedules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import com.tenniscourts.tenniscourts.TennisCourt;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	List<Schedule> findByStartDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);

    Schedule findByStartDateTimeBetweenAndTennisCourt(LocalDateTime StartDateTime, LocalDateTime StartDateTimePlusOne, TennisCourt tennisCourt);

    List<Schedule> findByIdNotInAndStartDateTimeGreaterThan(List<Long> id, LocalDateTime startDateTime);

    List<Schedule> findByEndDateTimeBefore(LocalDateTime endDateTime);

}