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

    public GuestDTO createGuest(GuestDTO guestDTO) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guestDTO)));
    }

    public GuestDTO guestById(Long id) {
        return guestRepository.findById(id).map(guestMapper::map).orElseThrow(() ->
                new EntityNotFoundException("Guest not found.")
        );
    }

    public List<GuestDTO> guestByName(String name) {
        Optional<List<Guest>> guests = guestRepository.findGuestByName(name);
        if(!guests.isPresent())
            throw new EntityNotFoundException("Guest not found.");

        return guestMapper.map(guests.get());
    }

    public List<GuestDTO> guestList() {
        return guestMapper.map(guestRepository.findAll());

    }

    public GuestDTO updateGuest(Long id, GuestDTO guestDTOs) {
        Guest guest = guestRepository.findById(id).get();
        guest.setName(guestDTO.getName());
        guest = guestRepository.save(guest);
        return guestMapper.map(guest);
    }

    public void deleteGuest(Long id) {
        guestRepository.deleteById(id);
    }


}
