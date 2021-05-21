package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.hibernate.internal.util.StringHelper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GuestsService {

    private final GuestsRepository guestsRepository;

    private final GuestsMapper guestsMapper;

    public GuestDTO addGuest(CreateGuestRequestDTO createGuestRequestDTO) {
        return guestsMapper.map(guestsRepository.saveAndFlush(guestsMapper.map(createGuestRequestDTO)));
    }

    public GuestDTO findGuest(Long guestId) {
        return guestsRepository.findById(guestId).map(guestsMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Guest not found."));
    }

    public List<GuestDTO> findGuests(@Nullable String name) {
        List<Guest> guests;
        if (StringHelper.isNotEmpty(name)) {
            guests = guestsRepository.findAllByNameContainingIgnoreCase(name);
        } else {
            guests = guestsRepository.findAll();
        }
        // Note: I'd rather add QueryDSL to the project so I could add QuerydslPredicateExecutor to the repository
        return guests.stream().map(guestsMapper::map).collect(Collectors.toList());
    }

    public GuestDTO updateGuest(Long guestId, UpdateGuestRequestDTO updateGuestRequestDTO) {
        Guest guest = guestsRepository.findById(guestId)
                .orElseThrow(() -> new EntityNotFoundException("Guest not found."));
        guest.setName(updateGuestRequestDTO.getName());
        return guestsMapper.map(guestsRepository.saveAndFlush(guest));
    }

    public void deleteGuest(Long guestId) {
        guestsRepository.deleteById(guestId);
    }
}
