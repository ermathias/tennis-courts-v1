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

    public GuestDTO addGuest(GuestDTO guest) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guest)));
    }

    public GuestDTO findGuestById(Long id) {
        return guestRepository.findById(id).map(guestMapper::map).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }

    public List<Guest> findGuestByName(String name) {
        List<Guest>  guestList = guestRepository.findByName(name);
        if(guestList != null) {
            return guestList;
        }else{
            throw new EntityNotFoundException("Guest not found.");
        }
    }

    public List<Guest> findAllGuest() {
        List<Guest>  guestList = guestRepository.findAll();
        if(guestList != null) {
            return guestList;
        }else{
            throw new EntityNotFoundException("Guest not found.");
        }
    }

    public GuestDTO updateGuest(GuestDTO guestDTO) {
        return guestMapper.map(this.update(guestDTO));
    }

    private Guest update(GuestDTO guestDTO) {
        return guestRepository.findById(guestDTO.getId()).map(guest -> {
            guest.setName(guestDTO.getName());
            return guestRepository.save(guest);
        }).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }

    public Long deleteGuestById(Long id) {
        return guestRepository.findById(id).map(guest -> {
            guestRepository.deleteById(guest.getId());
            return guest.getId();
        }).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }
}
