package com.tenniscourts.guests;

import com.tenniscourts.exceptions.AlreadyExistsEntityException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    public GuestDTO addGuest(String name) {

        if (guestRepository.findByName(name).isEmpty()) {
            var guest = Guest.builder().name(name).build();
            return guestMapper.map(guestRepository.saveAndFlush(guest));
        } else {
            throw new AlreadyExistsEntityException("This guest already exist!!");
        }
    }

    public void deleteGuest(Long id) {
        var guest = guestRepository.findById(id);

        if (guest.isPresent()) {
            guestRepository.delete(guest.get());
        } else {
            throw new EntityNotFoundException("Guest not found");
        }
    }

    public GuestDTO updateGuest(GuestDTO guest) {
        return guestMapper.map(guestRepository.save(guestMapper.map(guest)));
    }

    public List<GuestDTO> listAllGuests() {
        var schedules = guestRepository.findAll();
        return Optional.of(schedules).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }

    public GuestDTO findByGuestId(Long guestId) {
        return guestRepository.findById(guestId).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }

    public GuestDTO findByGuestName(String guestName) {
        return guestRepository.findByName(guestName).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }
}
