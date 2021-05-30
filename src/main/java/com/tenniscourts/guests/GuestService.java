package com.tenniscourts.guests;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.EntityNotFoundException;

@Service
public class GuestService {
	
	@Autowired
	private GuestRepository guestRepository;
	
	@Autowired(required=false)
	GuestMapper guestMapper;
	
	public GuestDTO createGuest(String name) {
		CreateGuestDTO createGuestDTO = new CreateGuestDTO();
		createGuestDTO.setName(name);
		return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(createGuestDTO)));
	}
	
	public GuestDTO findGuestById(Long guestId) {
		return guestRepository.findById(guestId).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
	}
	
	public GuestDTO findGuestByName(String guestName) {
		return guestMapper.map(guestRepository.findGuestByName(guestName));
	}
	
	public String deletGuest(Long guestId) {
		 guestRepository.deleteById((guestId));
		 return "Guest Deleted Successfully";
	}
	
	public GuestDTO updateGuest(CreateGuestDTO createGuestDTO) {
		return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(createGuestDTO)));
	}
	
	public List<GuestDTO> getAllGuests(){
		return guestMapper.map(guestRepository.findAll());
	}
	
	
	/*private Guest cancel(Long guestId) {
		return guestRepository.findById(guestId).map(guest -> {
			
			return this.updateGuest(guest);
			
		}).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
	}*/
}
