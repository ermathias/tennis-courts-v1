package com.tenniscourts.schedules;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);
    
    List<Schedule> findByTennisCourt_IdAndStartDateTimeBetween(Long tennisCourtId, LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT s FROM Schedule s WHERE (s.tennisCourt.id = :#{#tennisCourtId}) "
			+ "AND (:#{#dateTime} BETWEEN s.startDateTime AND DATEADD(MINUTE, -1, s.endDateTime) "
			+ "OR :#{#dateTimePlus} BETWEEN s.startDateTime AND s.endDateTime)")	
    List<Schedule> checkTennisCourtAvailability(@Param("tennisCourtId") Long tennisCourtId, @Param("dateTime") LocalDateTime dateTime,
    		@Param("dateTimePlus") LocalDateTime dateTimePlus);
}