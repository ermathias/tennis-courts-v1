package com.tenniscourts.schedules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);

    Schedule findByStartDateTime(LocalDateTime startDateTime);

    List<Schedule> findByStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqual(LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query(value = "SELECT s.*\n" +
            "                  FROM schedule s\n" +
            "                   LEFT JOIN reservation r\n" +
            "                       ON r.schedule_id = s.id\n" +
            "                   LEFT JOIN TENNIS_COURT t\n" +
            "                      ON t.id = s.TENNIS_COURT_ID\n" +
            "                  WHERE r.schedule_id IS NULL\n" +
            "                   AND t.id = :tennisCourtId",
            nativeQuery = true)
    List<Schedule> findFreeSchedulesByTennisCourtId(@Param("tennisCourtId") Long tennisCourtId);
}