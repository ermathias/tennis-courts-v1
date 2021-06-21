package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GuestService {

    private static final String ERROR_MSG_GUEST_NOT_FOUND = "Guest not found.";

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    public GuestDTO createGuest(GuestDTO guest) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guest)));
    }

    public List<GuestDTO> findAllGuests() {
        return guestMapper.map(guestRepository.findAll());
    }

    public GuestDTO findGuestById(Long guestId) {

        return guestMapper.map(guestRepository.findById(guestId).orElseThrow(() -> {
            throw new EntityNotFoundException(ERROR_MSG_GUEST_NOT_FOUND);
        }));
    }

    public List<GuestDTO> findGuestByName(String name) {
        return guestMapper.map(guestRepository.findByName(name));
    }

    public GuestDTO updateGuest(Long guestId, GuestDTO newGuest) {
        Guest guest = guestRepository.findById(guestId).orElseThrow(() -> {
            throw new EntityNotFoundException(ERROR_MSG_GUEST_NOT_FOUND);
        });

        guest.setName(newGuest.getName());
        return guestMapper.map(guestRepository.save(guest));
    }

    public void deleteGuest(Long guestId) {
        guestRepository.deleteById(guestId);
    }
}
