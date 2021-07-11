package com.tenniscourts.guests;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {

 }
