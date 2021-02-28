package com.tenniscourts.reservations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import com.tenniscourts.schedules.Schedule;

@Repository
public interface ReservationRepository extends ReservationRepositoryCustom, JpaRepository<Reservation, Long> {

    List<Reservation> findBySchedule_Id(Long scheduleId);

    List<Reservation> findByReservationStatusAndSchedule_StartDateTimeGreaterThanEqualAndSchedule_EndDateTimeLessThanEqual(ReservationStatus reservationStatus, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Reservation> findByReservationStatus(ReservationStatus status);

    boolean existsByScheduleAndReservationStatus(Schedule schedule, ReservationStatus status);
}