package com.tenniscourts.guests;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    Guest findFirstById(Long guestId);

    List<Guest> findByNameContainingIgnoreCase(String guestName);
}
