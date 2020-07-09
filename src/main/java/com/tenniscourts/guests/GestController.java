package com.tenniscourts.guests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GestController {

    @Autowired
    private GuestService guestService;

    @PostMapping("/addGuest")
    public ResponseEntity<Guest> addGuest(@RequestBody GuestDTO guestDTO) {
        Guest guest = guestService.addGuest(guestDTO);
        return ResponseEntity.ok(guest);
    }

    @GetMapping("/findGuestById")
    public ResponseEntity<Optional<Guest>> findGuestById(Long id) {
        Optional<Guest> guest = guestService.findGuestById(id);
        return ResponseEntity.ok(guest);
    }

    @GetMapping("/findAllGuests")
    public ResponseEntity<List<Guest>> findAllGuest() {
        List<Guest> guestsList = guestService.findAllGuest();
        return ResponseEntity.ok(guestsList);
    }

    @GetMapping("/findGuestByName")
    public ResponseEntity<Optional<Guest>> findGuestById(String name) {
        Optional<Guest> guest = guestService.findGuestByName(name);
        return ResponseEntity.ok(guest);
    }

    @DeleteMapping("/deleteGuestById")
    public ResponseEntity<String> deleteGuestById(Long id) {
        guestService.deleteGuestById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateGuestById")
    public ResponseEntity<Optional<Guest>> upateGuestById(Long id, String name) {
        Optional<Guest> guest = guestService.findGuestById(id);
        if (!guest.isPresent()) return ResponseEntity.notFound().build();
        Guest existingGuest = guest.get();
        guestService.updateGuest(existingGuest, name);
        return ResponseEntity.ok(guest);
    }
}
