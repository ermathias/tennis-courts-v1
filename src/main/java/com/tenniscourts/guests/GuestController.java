package com.tenniscourts.guests;

import java.util.Collection;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.guests.to.GuestCreateDTO;
import com.tenniscourts.guests.to.GuestDTO;
import com.tenniscourts.guests.to.GuestUpdateDTO;

@RequestMapping("guest")
@RestController
public class GuestController
	extends
	BaseRestController {

	private final GuestService service;

	@Autowired
	public GuestController(
		final GuestService service) {
		super();
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<Collection<GuestDTO>> find(
		@RequestParam(
			required = false) final String name) {
		if(Objects.isNull(name)) {
			Collection<GuestDTO> response = service.findAll();
			return ResponseEntity.ok(response);
		} else {
			Collection<GuestDTO> response = service.findByName(name);
			return ResponseEntity.ok(response);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<GuestDTO> find(
		@PathVariable final long id) {
		GuestDTO response = service.find(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<GuestDTO> save(
		@Valid @RequestBody final GuestCreateDTO request) {
		GuestDTO response = service.save(request);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<GuestDTO> update(
		@PathVariable final long id,
		@Valid @RequestBody final GuestUpdateDTO request) {
		GuestDTO response = service.update(id, request);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(
		@PathVariable final long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
