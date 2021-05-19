package com.tenniscourts.schedules;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	@Query("select sc from Schedule where sc.tennisCourt=(:id) order by sc.startDateTime")
    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(@Param ("id") Long id);
	@Query("select sc from Schedule where sc.startDateTime=(:strtDt) and sc.endDateTime=(:endDt)")
    List<Schedule> findSchedulesByStartDateTimeEndDateTime(@Param ("strtDt") LocalDateTime startDate, @Param ("endDt") LocalDateTime endDate);
}