package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    public GuestDTO addGuest(GuestDTO guestDTO) {
        final Guest newGuest = guestMapper.map(guestDTO);
        guestRepository.saveAndFlush(newGuest);
        return guestMapper.map(newGuest);
    }

    // left for simple name update
    public GuestDTO updateGuest(GuestDTO guestDTO) {
        final Guest existingGuest = guestMapper.map(findGuestById(guestDTO.getId()));
        existingGuest.setName(guestDTO.getName());
        guestRepository.saveAndFlush(existingGuest);
        return guestMapper.map(existingGuest);
    }

    public boolean deleteGuest(Long guestId) {
        guestRepository.deleteById(guestId);
        return true;
    }

    public GuestDTO findGuestById(Long guestId) {
        final Guest existingGuest = guestRepository.findById(guestId)
                .<EntityNotFoundException>orElseThrow(() -> { throw new EntityNotFoundException("Guest not found."); });
        return guestMapper.map(existingGuest);
    }

    public GuestDTO findGuestByName(String guestName) {
        final GuestDTO guest = guestMapper.map(guestRepository.findByName(guestName));
        if (guest == null) {
            throw new EntityNotFoundException("Guest with given name not found. ");
        }
        return guest;
    }

    public List<GuestDTO> listAllGuests() {
        final List<Guest> allGuests = guestRepository.findAll();
        return guestMapper.map(allGuests);
    }
}
