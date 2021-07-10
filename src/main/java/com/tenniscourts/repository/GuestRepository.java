package com.tenniscourts.repository;

import com.tenniscourts.model.Guest;
import com.tenniscourts.model.Reservation;
import com.tenniscourts.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    Optional<Guest> findByName(String name);

}
