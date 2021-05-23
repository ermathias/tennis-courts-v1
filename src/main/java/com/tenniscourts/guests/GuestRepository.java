package com.tenniscourts.guests;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tenniscourts.reservations.Reservation;


public interface GuestRepository extends JpaRepository<Guest, Long> {

}
