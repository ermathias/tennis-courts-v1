package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@AllArgsConstructor
@RestController
@RequestMapping("/guest")
public class GuestController extends BaseRestController {
	@Autowired
    private final GuestService guestService;

    @ApiOperation("Add a Guest")
    @PostMapping
    public ResponseEntity<Void> addGuest(@Valid @RequestBody CreateGuestDTO createGuestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(createGuestDTO).getId())).build();
    }

    @ApiOperation("List all Guests")
    @GetMapping
    public ResponseEntity<List<Guest>> listGuests() {
        return ResponseEntity.ok(guestService.listGuests());
    }

    @ApiOperation("Find a Guest by Id")
    @GetMapping("/id/{guestId}")
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }
 
    @ApiOperation("Find a Guest by Name")
    @GetMapping("/name/{guestName}")
    public ResponseEntity<GuestDTO> findGuestByName(@PathVariable String guestName) {
        return ResponseEntity.ok(guestService.findGuestByName(guestName));
    }

    @ApiOperation("Update a Guest")
    @PutMapping
    public ResponseEntity<GuestDTO> updateGuest(@Valid @RequestBody UpdateGuestDTO updateGuestDTO) {
      return ResponseEntity.ok(guestService.updateGuest(updateGuestDTO));
    }
    
    @ApiOperation("Delete a Guest by Id")
    @DeleteMapping("/{guestId}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long guestId) {
      guestService.deleteGuest(guestId);
      return ResponseEntity.ok().build();
    }

}
