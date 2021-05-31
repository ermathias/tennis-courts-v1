package com.tenniscourts.guests;

import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface GuestService {

    List<Guest> findAll();

    Guest findGuestById(Long idGuest);

    Guest findGuestByName(String nameGuest);

    void updateGuest(Long idGuest, LocalDateTime dcGuest, LocalDateTime duGuest, String incGuest, String inuGuest, Long ucGuest, Long uuGuest, String nameGuest);

    void save(Guest g);

    void delete(Guest g);
}
