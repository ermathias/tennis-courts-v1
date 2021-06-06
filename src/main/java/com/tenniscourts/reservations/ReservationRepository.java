package com.tenniscourts.reservations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findFirstById(Long reservationId);

    @Query(value = "select * from RESERVATION r left join SCHEDULE s on r.SCHEDULE_ID = s.id where s.END_DATE_TIME <= :timeNow",
    nativeQuery = true)
    List<Reservation> findAllPastReservations(Date timeNow);
}
