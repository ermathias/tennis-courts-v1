package com.tenniscourts.guests;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class GuestController extends BaseRestController implements GuestAPI {

    private final GuestService guestService;
    

    @PostMapping("/guests")
    public ResponseEntity<Void> createGuest(@RequestBody @Valid CreateGuestRequestDTO createGuestRequestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.createGuest(createGuestRequestDTO).getId())).build();
    }

    @PutMapping("/guests/{guestId}")
    public ResponseEntity<GuestDTO> updateGuestName(@PathVariable Long guestId, @RequestParam String name) {
    	
    	return ResponseEntity.ok(guestService.updateGuest(guestId, name));
    }

    @GetMapping("/guests/{guestId}")
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.findById(guestId));
    }

    @GetMapping("/guests/name/{name}")
    public ResponseEntity<GuestDTO> findGuestByName(@PathVariable String name) {
    	return ResponseEntity.ok(guestService.findByName(name));
    }

    @GetMapping("/guests")
    public ResponseEntity<List<GuestDTO>> findAll() {
    	return ResponseEntity.ok(guestService.findAll());
    }

    @DeleteMapping("/guests/{guestId}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long guestId) {
    	guestService.deleteGuest(guestId);
        return ResponseEntity.ok().build();
    }
}
