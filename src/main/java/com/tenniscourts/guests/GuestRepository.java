package com.tenniscourts.guests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    // TODO: Fix-me to uppercase or lowercase
    @Query(value = "select t from Guest t where t.name like %?1%")
    List<Guest> findByName(String name);
}
