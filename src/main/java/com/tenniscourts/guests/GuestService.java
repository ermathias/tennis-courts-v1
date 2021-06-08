package com.tenniscourts.guests;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.EntityNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GuestService {
	
	private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;
    
    public GuestDTO addGuest(CreateUpdateGuestRequestDTO createUpdateGuestRequestDTO) {
    	return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(createUpdateGuestRequestDTO)));
    }
    
    public GuestDTO findDTOById(Long guestId) {
    	 return guestRepository.findById(guestId).map(guestMapper::map).orElseThrow(() -> {
             throw new EntityNotFoundException("Guest not found.");
         });
    }
    
    public Guest findById(Long guestId) {
    	return guestRepository.findById(guestId).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }
    
    public Page<GuestDTO> findByName(String guestName, Integer page, Integer linesPerPage, String orderBy, String direction) {
    	PageRequest pageable = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    	Page<Guest> guests = guestRepository.findByNameContainingIgnoreCase(guestName, pageable);
    	return guests.map(guest -> guestMapper.map(guest));
    }
    
    public Page<GuestDTO> findAll(Integer page, Integer linesPerPage, String orderBy, String direction) {
    	PageRequest pageable = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    	Page<Guest> guests = guestRepository.findAll(pageable);
    	return guests.map(guest -> guestMapper.map(guest));
    }
    
    public void delete(Long guestId) {
    	findById(guestId);
    	guestRepository.deleteById(guestId);
    }
    
    public GuestDTO update(CreateUpdateGuestRequestDTO createUpdateGuestRequestDTO) {
    	Guest guest = findById(createUpdateGuestRequestDTO.getId());
    	guest.setName(createUpdateGuestRequestDTO.getName());
    	return guestMapper.map(guestRepository.saveAndFlush(guest));
    }
 
}
