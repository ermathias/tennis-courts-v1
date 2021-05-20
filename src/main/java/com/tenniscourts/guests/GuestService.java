package com.tenniscourts.guests;


import com.tenniscourts.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private GuestMapper guestMapper;

    public GuestDTO addGuest(GuestDTO guest) {
        return guestMapper.map(guestRepository.save(guestMapper.map(guest)));
    }

    @Transactional
    public GuestDTO updateGuest(GuestDTO guest) {
        Optional<Guest> guestObj = guestRepository.findById(guest.getId());
        if(guestObj.isPresent()) {
            guestRepository.update(guest.getId(),guest.getName());
            return guestMapper.map(guestRepository.findById(guest.getId()).get());
        }else {
            throw new EntityNotFoundException("Guest not found.");
        }
    }

    public GuestDTO findGuestById(Long id) {
        return guestRepository.findById(id).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("GuestId not found.");
        });
    }

    public List<GuestDTO> findGuestByName(String name) {
        return guestRepository.findByName(name).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("GuestName not found.");
        });
    }

    public List<GuestDTO> fetchAll(){
        return guestMapper.map(guestRepository.findAll());
    }

    public List<GuestDTO> deleteGuest(Long id){
        guestRepository.deleteById(id);
        return guestMapper.map(guestRepository.findAll());
    }
}


