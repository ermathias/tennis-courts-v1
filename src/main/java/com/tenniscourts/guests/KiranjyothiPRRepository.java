package com.tenniscourts.guests;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KiranjyothiPRRepository extends JpaRepository<Guest, Long> {

    Optional<Guest> findByName(String name);

}
