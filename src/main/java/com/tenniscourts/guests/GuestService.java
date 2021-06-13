package com.tenniscourts.guests;

import com.tenniscourts.exceptions.AlreadyExistsEntityException;
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

    public GuestDTO findGuestById(Long guestId) {
        return guestRepository.findById(guestId).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format("Guest with id %s not found.", guestId));
        });
    }

    public GuestDTO findGuestByName(String guestName) {
        return guestRepository.findByName(guestName).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format("Guest with name %s not found.", guestName));
        });
    }

    public List<GuestDTO> findAllGuests() {
        return guestRepository.findAll().stream().map(guestMapper::map).collect(Collectors.toList());
    }

    public void createGuest(CreateGuestRequestDTO createGuestRequestDTO) {
        guestRepository.save(guestMapper.map(createGuestRequestDTO));
    }

    public void deleteGuest(Long guestIdToDelete) {
        if (!existsById(guestIdToDelete)) {
            throw new EntityNotFoundException(String.format("Guest with id %s not found.", guestIdToDelete));
        }
        guestRepository.delete(guestMapper.map(findGuestById(guestIdToDelete)));
    }

    public void updateGuest(Long guestToUpdateId, GuestDTO updatedGuestDTO) {
        if (!existsById(guestToUpdateId)) {
            throw new EntityNotFoundException(String.format("Guest with id %s not found.", guestToUpdateId));
        }
        Guest guestToUpdate = guestRepository.getOne(guestToUpdateId);

        guestToUpdate.setName(updatedGuestDTO.getName());
        guestRepository.save(guestToUpdate);
    }

    private boolean existsById(Long guestId) {
        return guestRepository.existsById(guestId);
    }
}
