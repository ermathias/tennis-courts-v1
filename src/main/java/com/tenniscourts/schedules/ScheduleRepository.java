package com.tenniscourts.schedules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query(value = "select sc from schedule where sc.tennisCourt=?1 order by sc.startDateTime",nativeQuery = true)
    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);

    @Query(value = "select sc from schedule where sc.startDateTime=?1 and sc.endDateTime=?2",nativeQuery = true)
    List<Schedule> findByStartDateTime_EndDateTime(LocalDateTime startDate, LocalDateTime endDate);
}