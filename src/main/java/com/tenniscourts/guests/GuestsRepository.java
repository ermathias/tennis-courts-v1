package com.tenniscourts.guests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface GuestsRepository extends JpaRepository<Guest, Long> {

    List<Guest> findAllByNameContainingIgnoreCase(String name);
}
