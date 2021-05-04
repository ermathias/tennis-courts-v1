package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GuestService {
    @Autowired
    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    public GuestDTOs createGuest(GuestDTOs guestDTOs) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guestDTOs)));
    }

    public GuestDTOs readGuestById(Long id) {
        return guestRepository.findById(id).map(guestMapper::map).orElseThrow(() ->
                new EntityNotFoundException("Guest not found.")
        );
    }

    public List<GuestDTOs> readGuestByName(String name) {
        Optional<List<Guest>> guests = guestRepository.findGuestByName(name);
        if(!guests.isPresent())
            throw new EntityNotFoundException("Guest not found.");

        return guestMapper.map(guests.get());
    }

    public List<GuestDTOs> readListAllGuest() {
        return guestMapper.map(guestRepository.findAll());

    }

    public GuestDTOs updateGuest(Long id, GuestDTOs guestDTOs) {
        Guest guest = guestRepository.findById(id).get();
        guest.setName(guestDTOs.getName());
        guest = guestRepository.save(guest);
        return guestMapper.map(guest);
    }

    public void deleteGuest(Long id) {
        guestRepository.deleteById(id);
    }
}
