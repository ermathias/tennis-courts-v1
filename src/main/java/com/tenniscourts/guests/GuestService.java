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

    public GuestRequestDTO addGuest(GuestRequestDTO guestRequestDTO) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guestRequestDTO)));
    }

    public GuestRequestDTO updateGuest(Long id, GuestRequestDTO guestRequestDTO) {
        Guest guest = guestRepository.findById(id).get();
        guest.setName(guestRequestDTO.getName());
        guest = guestRepository.save(guest);
        return guestMapper.map(guest);
    }

    public void deleteGuest(Long id) {
        guestRepository.deleteById(id);
    }

    public GuestRequestDTO findGuestById(Long id) {
        return guestRepository.findById(id).map(guestMapper::map).orElseThrow(() ->
                new EntityNotFoundException("Guest not found.")
        );
    }

    public java.util.List<GuestRequestDTO> findGuestByNmae(String name) {
        Optional<List<Guest>> guests = guestRepository.findGuestByName(name);
        if(!guests.isPresent())
            throw new EntityNotFoundException("Guest not found.");

        return guestMapper.map(guests.get());
    }

    public java.util.List<GuestRequestDTO> listAllGuest() {
        return guestMapper.map(guestRepository.findAll());

    }
}
