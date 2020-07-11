package com.tenniscourts.guests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GuestController {

    @Autowired
    private GuestService guestService;

    @PostMapping("/addGuest")
    public ResponseEntity<Guest> addGuest(@RequestBody GuestDTO guestDTO) {
        Guest guest = guestService.addGuest(guestDTO);
        return ResponseEntity.ok(guest);
    }

    @GetMapping("/findGuestById")
    public ResponseEntity<Guest> findGuestById(Long id) {
        Guest guest = guestService.findGuestById(id);
        if (guest==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(guest);
    }

    @GetMapping("/findAllGuests")
    public ResponseEntity<List<Guest>> findAllGuest() {
        List<Guest> guestsList = guestService.findAllGuest();
        return ResponseEntity.ok(guestsList);
    }

    @GetMapping("/findGuestByName")
    public ResponseEntity<Guest> findGuestByName(String name) {
        Guest guest = guestService.findGuestByName(name);
        if (guest==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(guest);
    }

    @DeleteMapping("/deleteGuestById")
    public ResponseEntity deleteGuestById(Long id) {
        Guest guest = guestService.findGuestById(id);
        if (guest==null) return ResponseEntity.notFound().build();
        guestService.deleteGuestById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateGuestById")
    public ResponseEntity<Guest> upateGuestById(Long id, String name) {
        Guest guest = guestService.findGuestById(id);
        if (guest==null) return ResponseEntity.notFound().build();
        guest = guestService.updateGuest(guest, name);
        return ResponseEntity.ok(guest);
    }
}
