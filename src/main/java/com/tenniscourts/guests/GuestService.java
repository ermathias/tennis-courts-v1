package com.tenniscourts.guests;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class GuestService {

    @Autowired
    public GuestRepository guestRepository;

    public Guest addGuest(GuestDTO guestDTO) {
        Guest guest = new Guest();
        guest.setName(guestDTO.getName());
        guestRepository.save(guest);
        return guest;
    }

    public Guest findGuestById(Long id) {
        Optional<Guest> guestOpt = guestRepository.findById(id);
        if (!guestOpt.isPresent()) return null;
        Guest guest = guestOpt.get();
        return guest;
    }

    public List<Guest> findAllGuest() {
        List<Guest> guestsList = new ArrayList<>();
        guestsList = guestRepository.findAll();
        return guestsList;
    }


    public Guest findGuestByName(String name) {
        Optional<Guest> guestOpt = guestRepository.findByName(name);
        if (!guestOpt.isPresent()) return null;
        Guest guest = guestOpt.get();
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
