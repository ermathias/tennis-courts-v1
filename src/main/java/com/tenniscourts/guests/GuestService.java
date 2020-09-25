package com.tenniscourts.guests;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.EntityNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    public GuestDTO createGuest(CreateGuestRequestDTO createGuestRequestDTO) {
        Guest guest = guestRepository.save(guestMapper.map(createGuestRequestDTO));
		return guestMapper.map(guest);
    }

    public GuestDTO updateGuest(Long id, String name) {
    	
    	Guest guest = guestRepository.findById(id)
    								 .orElseThrow(new EntityNotFoundException("Guest not found."));
    	guest.setName(name);
    	
    	return guestMapper.map(guestRepository.save(guest));
    }

    public GuestDTO findById(Long guestId) {
    	
        return guestRepository.findById(guestId)
        					  .map(guestMapper::map)
        					  .orElseThrow(new EntityNotFoundException("Guest not found."));
    }

    public GuestDTO findByName(String guestName) {
    	
    	return guestRepository.findByName(guestName)
    						  .map(guestMapper::map)
    						  .orElseThrow(new EntityNotFoundException("Guest not found."));
    }
    
    public List<GuestDTO> findAll() {
    	return guestMapper.map(guestRepository.findAll());
    }

    public void deleteGuest(Long reservationId) {
    	
    	if (guestRepository.findById(reservationId).isPresent()) {
    		guestRepository.deleteById(reservationId);
    	} else {
    		throw new EntityNotFoundException("Guest not found.");
    	}
    }
}
