package com.tenniscourts.schedules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);

    List<Schedule> findByStartDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT s.* FROM schedule s WHERE s.start_date_time > CURRENT_TIMESTAMP" +
            " AND not exists(SELECT null FROM reservation WHERE schedule_id = s.id AND reservation_status = 'READY_TO_PLAY')", nativeQuery = true)
    List<Schedule> findAllAvailable();
}