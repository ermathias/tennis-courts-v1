package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
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

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/guests")
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @PostMapping
    public ResponseEntity<GuestDTO> createGuest(@RequestBody GuestDTO guest) {
        return ResponseEntity.created(locationByEntity(guestService.createGuest(guest).getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<GuestDTO>> findAllGuests() {
        return ResponseEntity.ok(guestService.findAllGuests());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable("id") Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @GetMapping(params = {"name"})
    public ResponseEntity<List<GuestDTO>> findAllByTennisCourtId(@RequestParam("name") String name) {
        return ResponseEntity.ok(guestService.findGuestByName(name));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GuestDTO> updateGuest(@PathVariable("id") Long id, @RequestBody GuestDTO guest) {
        return ResponseEntity.ok(guestService.updateGuest(id, guest));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable("id") Long id) {
        guestService.deleteGuest(id);
        return ResponseEntity.ok().build();
    }
}
