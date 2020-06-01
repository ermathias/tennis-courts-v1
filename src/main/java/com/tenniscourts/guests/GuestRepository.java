package com.tenniscourts.guests;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository
	extends
	JpaRepository<Guest, Long> {

	Collection<Guest> findByName(
		String name);
}
