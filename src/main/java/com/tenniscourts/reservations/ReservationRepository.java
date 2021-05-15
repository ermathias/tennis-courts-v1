package com.tenniscourts.reservations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tenniscourts.guests.Guest;
import com.tenniscourts.schedules.Schedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	List<Reservation> findBySchedule_Id(Long scheduleId);

	List<Reservation> findByReservationStatusAndSchedule_StartDateTimeGreaterThanEqualAndSchedule_EndDateTimeLessThanEqual(
			ReservationStatus reservationStatus, LocalDateTime startDateTime, LocalDateTime endDateTime);

	Optional<Reservation> findByScheduleAndGuest(Schedule schedule, Guest guest);

	List<Reservation> findAllByDateUpdateBetween(LocalDateTime fromDate, LocalDateTime toDate);

}
