package com.tenniscourts.repository;

import com.tenniscourts.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);

    List<Schedule> findByTennisCourtAndStartDateTime(Long id, LocalDateTime startDateTime);

    @Query("SELECT s FROM Schedule s WHERE s.startDateTime >= :startDate AND s.endDateTime <= :endDate")
    List<Schedule> findSchedulesBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}