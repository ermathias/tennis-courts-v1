package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/guests")
public class GuestController extends BaseRestController {
    private final GuestService guestService;

    @GetMapping()
    public ResponseEntity<List<Guest>> findAllGuests(){
        return ResponseEntity.ok(guestService.listAll());
    }

    @GetMapping("/id/{guestId}")
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable ("guestId") Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @GetMapping("/name/{guestName}")
    public ResponseEntity<List<GuestDTO>> findGuestByByName(@PathVariable ("guestName") String guestName) {
        return ResponseEntity.ok(guestService.findGuestByName(guestName));
    }

    @PostMapping
    public ResponseEntity<Void> addGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestDTO).getId())).build();
    }

    @PutMapping("/{guestId}")
    public ResponseEntity<GuestDTO> updateGuest(@PathVariable ("guestId") Long guestId, @RequestBody Guest guest) {
        locationByEntity(guestService.updateGuest(guestId, guest));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{guestId}")
    public ResponseEntity<?> deleteGuestById(@PathVariable ("guestId") Long guestId) {
        guestService.deleteGuestById(guestId);
        return ResponseEntity.ok().build();
    }
}
