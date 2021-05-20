package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service("Guest service")
public class GuestService {

    private static final String GUEST_NOT_FOUND = "Guest not found in the system.";

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private GuestMapper guestMapper;

    public List<GuestDTO> listAllGuests() {
        return guestMapper.guestsToGuestDTOs(guestRepository.findAll());
    }

    public GuestDTO addGuest(NewGuestDTO newGuestDTO) {
        return guestMapper.guestToGuestDTO(guestRepository.saveAndFlush(guestMapper.newGuestDTOToGuest(newGuestDTO)));
    }

    public GuestDTO updateGuest(GuestDTO guestDTO){
        Optional<Guest> guestOptional = guestRepository.findById(guestDTO.getId());

        if (guestOptional.isEmpty()){
            return addGuest(guestMapper.guestDTOtoNewGuestDTO(guestDTO));
        }

        Guest guest = guestOptional.get();
        guest.setName(guestDTO.getName());
        guestRepository.save(guest);
        return guestMapper.guestToGuestDTO(guest);
    }

    public GuestDTO findGuestById(Long guestId){
        return guestRepository.findById(guestId).map(guestMapper::guestToGuestDTO).orElseThrow(() -> {
            throw new EntityNotFoundException(GUEST_NOT_FOUND);
        });
    }

    public GuestDTO findGuestByName(String guestName) {
        return guestRepository.findByName(guestName).map(guestMapper::guestToGuestDTO).orElseThrow(() -> {
            throw new EntityNotFoundException(GUEST_NOT_FOUND);
        });
    }

    public void deleteGuest(long guestId){
        Guest guest = guestRepository.findById(guestId).orElseThrow(() -> {
            throw new EntityNotFoundException(GUEST_NOT_FOUND);
        });
        guestRepository.delete(guest);
    }
}
