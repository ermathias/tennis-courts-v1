package com.tenniscourts.schedules;

import com.tenniscourts.guests.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);

    @Query(value= "select * from schedule where id=:idschedule", nativeQuery = true)
    Schedule findScheduleById (@Param("idschedule") Long idschedule);

}