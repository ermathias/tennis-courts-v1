package com.tenniscourts.guests;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
	
	List<Guest> findByNameContains(String name);

}
