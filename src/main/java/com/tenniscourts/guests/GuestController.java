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

    @GetMapping("/findById/{id}")
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable("id") Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<GuestDTO> findGuestByName(@PathVariable("name") String guestName) {
        return ResponseEntity.ok(guestService.findGuestByName(guestName));
    }

    @GetMapping("/list")
    public ResponseEntity<List<GuestDTO>> findGuestByName() {
        return ResponseEntity.ok(guestService.findAllGuests());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> createGuest(@RequestBody CreateGuestRequestDTO createGuestRequestDTO) {
        guestService.createGuest(createGuestRequestDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{guestId}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long guestId) {
        guestService.deleteGuest(guestId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{guestId}")
    public ResponseEntity<Void> updateGuest(@PathVariable Long guestId, @RequestBody GuestDTO updatedGuestDTO) {
        guestService.updateGuest(guestId, updatedGuestDTO);

        return ResponseEntity.ok().build();
    }
}
