package com.tenniscourts.schedules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);

    Schedule findFirstById(Long scheduleId);

    @Query("SELECT s FROM Schedule s WHERE s.startDateTime > (:startDate) AND s.endDateTime < (:endDate)")
    List<Schedule> findSchedulesBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
}