package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    public GuestDTO addGuest(CreateGuestRequestDTO createGuestRequestDTO) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(createGuestRequestDTO)));
    }

    public void deleteGuest(Long guestId) {
        guestRepository.deleteById(guestId);
    }

    public GuestDTO findGuestById(Long guestId) {
        return guestRepository.findById(guestId).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }

    public List<GuestDTO> findAllGuests() {
        return guestRepository.findAll().stream().map(guestMapper::map).collect(Collectors.toList());
    }

    public List<GuestDTO> findGuestByName(String name) {
        return guestRepository.findByNameIgnoreCaseContaining(name).stream().map(guestMapper::map).collect(Collectors.toList());
    }

    public GuestDTO updateGuest(GuestDTO guestDTO) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guestDTO)));
    }
}
