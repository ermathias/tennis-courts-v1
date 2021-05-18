package com.tenniscourts.guests;

import com.tenniscourts.schedules.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GuestRepository extends JpaRepository<Guest, Long> {

    List<Guest> findByName(String name);
}
