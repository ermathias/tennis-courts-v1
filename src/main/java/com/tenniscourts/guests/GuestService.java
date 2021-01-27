package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GuestService {
    @Autowired
    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    public GuestDTO addGuest(GuestDTO guest) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guest)));
    }

    public GuestDTO findGuestById(Long id) {
        return guestRepository.findById(id).map(guestMapper::map).orElseThrow(() -> new EntityNotFoundException("Guest not found."));
    }

    public GuestDTO findGuestByName(String name) {
        return guestRepository.findByName(name).map(guestMapper::map).orElseThrow(() -> new EntityNotFoundException("Guest not found."));
    }

    public GuestDTO updateGuest(GuestDTO guestDTO, Long id) {
        Guest guest = guestRepository.findById(id).get();
        guest.setName(guestDTO.getName());
        guestRepository.save(guest);
        return guestDTO;
    }

    public GuestDTO removeGuest(Long guestId) {
        return guestMapper.map(this.remove(guestId));
    }

    private Guest remove(Long guestId) {
        return guestRepository.findById(guestId).map(guest -> {
            guestRepository.delete(guest);
            return guest;
        }).orElseThrow(() -> new EntityNotFoundException("Guest not found."));
    }

    public List<GuestDTO> findall() {
        List <Guest> guests = new ArrayList<>();
        guests = guestRepository.findAll();
        List <GuestDTO> guestDTOS = new ArrayList<>();
        guests.forEach(guest -> guestDTOS.add(guestMapper.map(guest)));
        return guestDTOS;
    }
}
