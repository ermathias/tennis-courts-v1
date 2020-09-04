package com.tenniscourts.guests;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GuestService {

	private final GuestRepository guestRepository;
	
	private final GuestMapper guestMapper;
	
	@PutMapping(value = "/guest")
    public GuestDTO createGuest(CreateGuestRequestDTO createGuestRequestDTO) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(createGuestRequestDTO)));
    }
	
	public Guest update(Guest guest) {
		if (!isValid(guest)) {
			return null;
		}

		return guestRepository.save(guest);
	}
	
	private boolean isValid(Guest guest) {
		
		return true;
	}
	
	public void delete(Long id) {
		guestRepository.deleteById(id);
	}
	
	public List<Guest> findAll() {
		return (List<Guest>) guestRepository.findAll();
	}
	
	public Optional<Guest> findById(Long id) {
		return guestRepository.findById(id);
	}
	
	public List<Guest> findByName(String name) {
		return guestRepository.findByName(name);
	}
}
