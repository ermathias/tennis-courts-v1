package com.tenniscourts.guests;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class GuestController extends BaseRestController {

	private final GuestService guestService;

	@PostMapping(value = "/v1/guest/create")
	public ResponseEntity<Void> createGuest(@RequestBody GuestDTO guestDTO) {

		return ResponseEntity.created(locationByEntity(guestService.createGuest(guestDTO).getId())).build();

	}

	@GetMapping(value = "/v1/guest/retrieve")
	public ResponseEntity<List<GuestDTO>> retrieveGuests() {

		return ResponseEntity.ok(guestService.retrieveGuests());

	}

	@GetMapping(value = "/v1/guest/{guestId}/retrieve")
	public ResponseEntity<GuestDTO> retrieveGuestById(@PathVariable Long guestId) {

		return ResponseEntity.ok(guestService.retrieveGuestById(guestId));

	}

	@PostMapping(value = "/v1/guest/retrieveByName")
	public ResponseEntity<GuestDTO> retrieveGuestByName(@RequestBody GuestDTO guestDTO) {

		return ResponseEntity.ok(guestService.retrieveGuestByName(guestDTO));

	}

	@PostMapping(value = "/v1/guest/{guestId}/update")
	public ResponseEntity<GuestDTO> update(@PathVariable Long guestId, @RequestBody GuestDTO guestDTO) {

		return ResponseEntity.ok(guestService.update(guestId, guestDTO));

	}
}
