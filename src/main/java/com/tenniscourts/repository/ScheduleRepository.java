package com.tenniscourts.repository;

import com.tenniscourts.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);
    List<Schedule> findAllByStartDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    Schedule findByTennisCourtIdAndStartDateTime(Long tennisCourtId, LocalDateTime initialDate);

}