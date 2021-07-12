package com.tenniscourts.guests;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	public ResponseEntity<Void> create(@RequestBody GuestDTO guestDTO) {

		return ResponseEntity.created(locationByEntity(guestService.create(guestDTO).getId())).build();

	}

	@GetMapping(value = "/v1/guest/retrieve")
	public ResponseEntity<List<GuestDTO>> retrieve() {

		return ResponseEntity.ok(guestService.retrieve());

	}

	@GetMapping(value = "/v1/guest/{guestId}/retrieve")
	public ResponseEntity<GuestDTO> retrieveById(@PathVariable Long guestId) {

		return ResponseEntity.ok(guestService.retrieveById(guestId));

	}

	@PostMapping(value = "/v1/guest/retrieveByName")
	public ResponseEntity<GuestDTO> retrieveByName(@RequestBody GuestDTO guestDTO) {

		return ResponseEntity.ok(guestService.retrieveByName(guestDTO));

	}

	@PostMapping(value = "/v1/guest/{guestId}/update")
	public ResponseEntity<GuestDTO> update(@PathVariable Long guestId, @RequestBody GuestDTO guestDTO) {

		return ResponseEntity.ok(guestService.update(guestId, guestDTO));

	}

	@DeleteMapping(value = "/v1/guest/{guestId}/delete")
	public ResponseEntity<GuestDTO> delete(@PathVariable Long guestId) {

		return ResponseEntity.ok(guestService.delete(guestId));

	}
}
