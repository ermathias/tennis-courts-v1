package com.tenniscourts.service;

import com.tenniscourts.dto.GuestDTO;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.mapper.GuestMapper;
import com.tenniscourts.model.Guest;
import com.tenniscourts.repository.GuestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;


    public GuestDTO create(GuestDTO guestDTO) {

        Guest guest = this.guestMapper.map(guestDTO);

        return guestMapper.map(guestRepository.save(guest));

    }

    public GuestDTO modify(GuestDTO guestDTO) {
        Optional<Guest> guest = this.guestRepository.findById(guestDTO.getId());
        GuestDTO result;
        if (guest.isPresent()){
            guest.get().setName(guestDTO.getName());
            result = guestMapper.map(guestRepository.saveAndFlush(guest.get()));
        } else {
            throw new
                    EntityNotFoundException
                    ("Cannot modify Guest with ID provided as it does not exist.");
        }
        return result;
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


    public List<GuestDTO> findAll() {
        List<Guest> guests = guestRepository.findAll();
        return guestMapper.map(guests);
    }



}
