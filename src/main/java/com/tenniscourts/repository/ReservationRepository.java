package com.tenniscourts.repository;

import com.tenniscourts.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.schedule.startDateTime <= :closingTime")
    List<Reservation> getHistoryReservation(@Param("closingTime") LocalDateTime referenceTime);

}
