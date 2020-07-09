package com.tenniscourts.guests;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class GuestService {

    @Autowired
    public GuestRepository guestRepository;

    public Guest addGuest(GuestDTO guestDTO) {
        Guest guest = new Guest();
        guest.setName(guestDTO.getName());
        guestRepository.save(guest);
        return guest;
    }

    public Optional<Guest> findGuestById(Long id) {
        Optional<Guest> guest = guestRepository.findById(id);
        return guest;
    }

    public List<Guest> findAllGuest() {
        List<Guest> guestsList = new ArrayList<>();
        guestsList = guestRepository.findAll();
        return guestsList;
    }


    public Optional<Guest> findGuestByName(String name) {
        Optional<Guest> guest = guestRepository.findByName(name);
        return guest;
    }

    public void deleteGuestById(Long id) {
        guestRepository.deleteById(id);
    }

    public Guest updateGuest(Guest guest, String name) {
        guest.setName(name);
        guestRepository.save(guest);
        return guest;
    }
}
