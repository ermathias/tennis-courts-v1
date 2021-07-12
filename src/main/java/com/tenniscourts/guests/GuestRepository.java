package com.tenniscourts.guests;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface GuestRepository extends JpaRepository<Guest, Long> {

	Optional<Guest> findByName(String guestName);

 }
