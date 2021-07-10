package com.tenniscourts.service;

import com.tenniscourts.dto.GuestDTO;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.mapper.GuestMapper;
import com.tenniscourts.model.Guest;
import com.tenniscourts.repository.GuestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;


    public GuestDTO create(GuestDTO guestDTO) {

        Guest guest = this.guestMapper.map(guestDTO);

        return guestMapper.map(guestRepository.save(guest));

    }

    public GuestDTO findById(Long guestId) {

        return guestRepository.findById(guestId).map(guestMapper::map).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Guest by id not found.");
        });

    }

    public GuestDTO findByName(String name) {

        return guestRepository.findByName(name).map(guestMapper::map).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Guest by name not found.");
        });

    }

    public GuestDTO cancel(Long guestId) {
        Guest guest = guestRepository.getOne(guestId);
        this.guestRepository.delete(guest);
        return guestMapper.map(guest);
    }



}
