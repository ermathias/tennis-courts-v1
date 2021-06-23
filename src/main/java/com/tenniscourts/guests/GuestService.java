package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GuestService {

    @Autowired
    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    public GuestDTO addGuest(CreateGuestRequestDTO createGuestRequestDTO) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(createGuestRequestDTO)));
    }

    public GuestDTO findGuestById(Long id) {
        return guestRepository.findById(id).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }

    public List<GuestDTO> findAllGuests() {
        return guestRepository.findAll().stream().map(guestMapper::map).collect(Collectors.toList());
    }

    public GuestDTO findGuestByName(String guestName) {
        return guestRepository.findByName(guestName).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }

    public void deleteGuestById(Long guestId) {

        Guest guest = guestRepository.findById(guestId).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");

        });
        guestRepository.delete(guest);

    }

    public GuestDTO updateGuest(GuestDTO guestDTO) {
        Guest guest = guestRepository.findById(guestDTO.getId()).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");

        });
        guest.setName(guestDTO.getName());
        guestRepository.saveAndFlush(guest);
        return guestMapper.map(guest);
    }
}
