package com.tenniscourts.guests;

import java.util.List;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GuestService {
    @Autowired
    private final GuestRepository guestRepository;

    @Autowired
    private final GuestMapper guestMapper;

    public List<Guest> listGuests (){
        return guestRepository.findAll();
    }

    public GuestDTO addGuest(GuestDTO tennisCourt) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(tennisCourt)));
    }

    public GuestDTO findGuestById(Long id) {
        return guestRepository.findById(id).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }

    public GuestDTO findGuestByName(String name) {
        return guestRepository.findByName(name).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }

    public GuestDTO updateGuest(UpdateGuestDTO updateGuestDTO) {
        Guest guest = guestRepository.findById(updateGuestDTO.getId()).orElseThrow(() -> {
          throw new EntityNotFoundException("Guest not found.");
        });
        guest.setName(updateGuestDTO.getName());
        guestRepository.save(guest);
        return guestRepository.findById(guest.getId()).map(guestMapper::map).get();
    }

    public void deleteGuest(Long guestId) {
      guestRepository.delete(guestRepository.findById(guestId).orElseThrow(() -> {
        throw new EntityNotFoundException("Guest not found.");
      }));
    }

  }
