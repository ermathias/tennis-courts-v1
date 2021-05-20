package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    public GuestDTO addGuest(CreateGuestRequestDTO createGuestRequestDTO) {
        Guest guest = Guest.builder()
                .name(createGuestRequestDTO.getName())
                .build();

        return this.guestMapper.map(this.guestRepository.save(guest));
    }

    public GuestDTO findGuestById(Long guestId) {
        return this.guestMapper.map(this.guestRepository.findById(guestId)
                .orElseThrow(() -> new EntityNotFoundException("Guest not found.")));
    }

    public List<GuestDTO> findGuestsByName(String guestName) {
        if (!StringUtils.hasText(guestName)) {
            throw new IllegalArgumentException("The guest name should not be empty");
        }
        return guestMapper.map(guestRepository.findAllByNameContainingIgnoreCase(guestName));
    }

    public GuestDTO updateGuest(Long guestId, CreateGuestRequestDTO createGuestRequestDTO) {
        Guest guest = this.guestRepository.findById(guestId)
                .orElseThrow(() -> new EntityNotFoundException("Guest not found."));
        this.guestMapper.map(createGuestRequestDTO, guest);
        return this.guestMapper.map(this.guestRepository.save(guest));
    }

    public GuestDTO deleteGuest(Long guestId) {
        return this.guestRepository.findById(guestId).map(guest -> {
            this.guestRepository.delete(guest);
            return this.guestMapper.map(guest);
        }).orElseThrow(() ->
            new EntityNotFoundException("Guest not found.")
        );
    }

    public List<GuestDTO> getAllGuests() {
        return this.guestMapper.map(this.guestRepository.findAll());
    }
}
