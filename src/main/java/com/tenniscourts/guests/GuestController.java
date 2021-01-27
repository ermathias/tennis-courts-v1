package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.reservations.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/guest")
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Void> addGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestDTO).getId())).build();
    }

    @GetMapping(value = "/findguestbyid/{guestId}")
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @GetMapping(value = "/findguestbyname/{name}")
    public ResponseEntity<GuestDTO> findGuestByName(@PathVariable String name) {
        return ResponseEntity.ok(guestService.findGuestByName(name));
    }

    @GetMapping(value = "/findall")
    public ResponseEntity<List<GuestDTO>> findAll() {
        return ResponseEntity.ok(guestService.findall());
    }

    @PutMapping(value = "update/{guestId}")
    public ResponseEntity<GuestDTO> updateGuest(@PathVariable Long guestId, @RequestBody GuestDTO guestDTO) {
        return ResponseEntity.ok(guestService.updateGuest(guestDTO, guestId));
    }

    @DeleteMapping(value = "/remove/{guestId}")
    public ResponseEntity<GuestDTO> removeGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.removeGuest(guestId));
    }

}
