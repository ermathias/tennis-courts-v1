package com.tenniscourts.reservations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	List<Reservation> findBySchedule_Id(Long scheduleId);

	List<Reservation> findByReservationStatusAndSchedule_StartDateTimeGreaterThanEqualAndSchedule_EndDateTimeLessThanEqual(
			ReservationStatus reservationStatus, LocalDateTime startDateTime, LocalDateTime endDateTime);

	Optional<Reservation> findByScheduleIdAndStartDateTimeAndEndDateTime(Long scheduleId, LocalDateTime startDateTime,
			LocalDateTime endDateTime);

//    List<Reservation> findByStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqualAndTennisCourt(LocalDateTime startDateTime, LocalDateTime endDateTime, TennisCourt tennisCourt);
}
