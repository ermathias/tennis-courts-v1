package com.tenniscourts.guests;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
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

import lombok.AllArgsConstructor;

//5. As a Tennis Court Admin, I want to be able to Create/Update/Delete/Find by id/Find by name/List all the guests
@AllArgsConstructor
@RestController
@RequestMapping("guests")
public class GuestController extends BaseRestController {
	
	private final GuestService guestService;

    @PostMapping
    public ResponseEntity<Void> addGuest(@Valid @RequestBody CreateUpdateGuestRequestDTO createUpdateGuestRequestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(createUpdateGuestRequestDTO).getId())).build();
    }

    @GetMapping(value = "{guestId}")
    public ResponseEntity<GuestDTO> findById(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.findDTOById(guestId));
    }
    
    @GetMapping(value = "findByName")
    public ResponseEntity<Page<GuestDTO>> findByName(@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="10") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="id") String orderBy,
			@RequestParam(value="direction", defaultValue="DESC") String direction,
			@RequestParam(value="guestName", defaultValue="") String guestName) {
        return ResponseEntity.ok(guestService.findByName(guestName, page, linesPerPage, orderBy, direction));
    }
    
    @GetMapping(value = "findAll")
    public ResponseEntity<Page<GuestDTO>> findAll(@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="10") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="id") String orderBy,
			@RequestParam(value="direction", defaultValue="DESC") String direction) {
        return ResponseEntity.ok(guestService.findAll(page, linesPerPage, orderBy, direction));
    }

    @DeleteMapping(value = "{guestId}")
    public ResponseEntity<Void> delete(Long guestId) {
    	guestService.delete(guestId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<GuestDTO> update(@Valid @RequestBody CreateUpdateGuestRequestDTO createUpdateGuestRequestDTO) {
        return ResponseEntity.ok(guestService.update(createUpdateGuestRequestDTO));
    }

}
