package com.tenniscourts.guests;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;

@RestController
public class GuestController extends BaseRestController {
	
	@Autowired
	GuestService guestService;
	
	@PostMapping(value = "/addGuest")
	public ResponseEntity<Void> createGuest(@RequestBody String name) {
		return ResponseEntity.created(locationByEntity(guestService.createGuest(name).getId())).build();
		
	}
	
	@GetMapping(value = "/getGuestById")
	public ResponseEntity<GuestDTO> findGuestById(@RequestBody Long guestId){
		return ResponseEntity.ok(guestService.findGuestById(guestId));
	}
	
	@PutMapping(value = "/updateGuest")
	public ResponseEntity<Void> updateGuest(@RequestBody CreateGuestDTO createGuestDTO) {
		return ResponseEntity.created(locationByEntity(guestService.updateGuest(createGuestDTO).getId())).build();
		
	}
	
	@DeleteMapping(value = "/deleteGuest")
	public ResponseEntity<String> updateGuest(@RequestBody Long guestId) {
		return ResponseEntity.ok(guestService.deletGuest(guestId));
	}
	
	@GetMapping(value = "/getAllGuests")
	public ResponseEntity<List<GuestDTO>> findAllGuest(){
		return ResponseEntity.ok(guestService.getAllGuests());
	}

}
