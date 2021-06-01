package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    public GuestDTO addGuest(GuestDTO guestDTO) {
        guestDTO.setId(null);
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guestDTO)));
    }

    public void updateGuest(GuestDTO guestDTO) {
        guestRepository.findById(guestDTO.getId()).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
        guestRepository.saveAndFlush(guestMapper.map(guestDTO));
    }

    public void deleteGuest(Long guestId) {
        guestRepository.findById(guestId).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
        guestRepository.deleteById(guestId);
    }

    public List<GuestDTO> findAllGuests() {
        return guestRepository.findAll().stream().map(guestMapper::map).collect(Collectors.toList());
    }

    public List<GuestDTO> findAllGuestsByName(String name) {
        return guestRepository.findAllByName(name).map(guestMapper::map).orElse(Collections.EMPTY_LIST);
    }

    public GuestDTO findGuestById(Long id) {
        return guestRepository.findById(id).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }
}
