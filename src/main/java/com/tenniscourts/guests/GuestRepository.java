package com.tenniscourts.guests;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
	
	Page<Guest> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
}
