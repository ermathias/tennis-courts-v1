package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Service
@AllArgsConstructor
public class GuestService {

    @Autowired
    private final GuestRepository guestRepository;

    @Autowired
    private final GuestMapper guestMapper;

    public List<Guest> listAllGuests() {
        return guestRepository.findAll();
    }

    public Guest findGuest(Long guestId) {
        Guest guest = guestRepository.findFirstById(guestId);

        if (guest == null) {
            throw new EntityNotFoundException("Guest not found.");
        }

        return guestRepository.findFirstById(guestId);
    }

    public List<Guest> findGuestByName(String guestName) {
        return guestRepository.findByNameContainingIgnoreCase(guestName);
    }

    public GuestDTO addGuest(GuestDTO guestDTO) {

        if (guestDTO.getBalance() == null) {
            guestDTO.setBalance(new BigDecimal(0));
        }

        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guestDTO)));
    }

    public Guest updateGuest(Long guestId, GuestDTO guestDTO) {
        Guest existingGuest = guestRepository.findFirstById(guestId);

        if (existingGuest == null) {
            throw new EntityNotFoundException("Guest not found.");
        }

        existingGuest.setBalance(guestDTO.getBalance());
        existingGuest.setName(guestDTO.getName());
        guestRepository.saveAndFlush(existingGuest);
        return existingGuest;
    }

    public void removeGuest(Long guestId) {
        Guest existingGuest = guestRepository.findFirstById(guestId);

        if (existingGuest == null) {
            throw new EntityNotFoundException("Guest not found.");
        }

        guestRepository.deleteById(guestId);
    }
}
