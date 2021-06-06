package com.tenniscourts.schedules;

import com.tenniscourts.reservations.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);

    Schedule findFirstById(Long scheduleId);

    List<Schedule> findByStartDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "select * from SCHEDULE s where s.TENNIS_COURT_ID = :tennisCourtId and " +
        "(s.START_DATE_TIME <= :startTime and s.END_DATE_TIME >= :startTime or " +
        "s.START_DATE_TIME <= :endTime and s.END_DATE_TIME >= :endTime)",
        nativeQuery = true)
    Schedule findExistingSchedule(Long tennisCourtId, LocalDateTime startTime, LocalDateTime endTime);

    @Query(value = "select * from SCHEDULE s where s.START_DATE_TIME > :timeNow and s.ID not in " +
        "(select SCHEDULE_ID from RESERVATION r where r.RESERVATION_STATUS in (0,3)) ",
        nativeQuery = true)
    List<Schedule> findAllFutureScheduleSlots(Date timeNow);
}