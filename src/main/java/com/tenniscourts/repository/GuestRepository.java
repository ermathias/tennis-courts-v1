package com.tenniscourts.repository;

import com.tenniscourts.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    List<Guest> findByName(String guestName);

}
