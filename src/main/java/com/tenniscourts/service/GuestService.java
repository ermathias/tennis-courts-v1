package com.tenniscourts.service;

import com.tenniscourts.dto.GuestDTO;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.model.guests.GuestMapper;
import com.tenniscourts.repository.GuestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    public GuestDTO addGuest(GuestDTO guestDTO) {
        return guestMapper.map(guestRepository.save(guestMapper.map(guestDTO)));
    }

    public GuestDTO patchGuest(GuestDTO guestDTO) {
        GuestDTO guest = guestRepository.findById(guestDTO.getId()).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });

        guest.setName(guestDTO.getName());

        return guestMapper.map(guestRepository.save(guestMapper.map(guest)));
    }

    public void deleteGuest(GuestDTO guestDTO) {
        guestRepository.delete(guestMapper.map(guestDTO));
    }

    public GuestDTO findById(Long id) {
        return guestMapper.map(
            guestRepository.findById(id).orElseThrow(() -> {
                throw new EntityNotFoundException("Guest not found.");
            })
        );
    }

    public List<GuestDTO> findByName(String name) {
        return guestRepository.findGuestByName(name).stream().map(guestMapper::map).collect(Collectors.toList());
    }

    public List<GuestDTO> findAll() {
        return guestRepository.findAll().stream().map(guestMapper::map).collect(Collectors.toList());
    }
}
