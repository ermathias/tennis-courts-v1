package com.tenniscourts.reservations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findBySchedule_Id(Long scheduleId);

    List<Reservation> findByReservationStatusNotIn(List<ReservationStatus> reservationStatus);

    List<Reservation> findByScheduleIdIn(List<Long> scheduleIds);
}
