package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GuestService
{
    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    public GuestDTO addGuest(CreateGuestRequestDTO guestRequestDTO) {
        return guestMapper.map(
                guestRepository.saveAndFlush(guestMapper.map(guestRequestDTO))
        );
    }

    public GuestDTO findGuest(Long guestId) {
        return guestRepository.findById(guestId).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    public List<GuestDTO> findGuestByName(String namePattern) {
        return guestMapper.map(guestRepository.findByNameContaining(namePattern));
    }

    public List<GuestDTO> findAllGuest() {
        return guestMapper.map(guestRepository.findAll());
    }

    public void removeGuest(Long guestId) {
        guestRepository.deleteById(guestId);
    }

    public GuestDTO updateGuest(Long guestId, CreateGuestRequestDTO guestRequestDTO) {
        Guest guest = guestRepository.findById(guestId).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
        guest.setName(guestRequestDTO.getName());
        return guestMapper.map(guestRepository.save(guest));
    }
}
