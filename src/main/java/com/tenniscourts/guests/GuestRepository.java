package com.tenniscourts.guests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    Optional<Guest> findById(Long id);

    Optional<Guest> findByName(String name);

    List<Guest> findAll();
}
