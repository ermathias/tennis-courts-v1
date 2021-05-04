package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.List;

@RestController
@RequestMapping("/guest")
@AllArgsConstructor
public class GuestController extends BaseRestController {
    @Autowired
    private final GuestService guestService;

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> createGuest(@RequestBody GuestDTOs guestDTOs) {
        return ResponseEntity.created(locationByEntity(guestService.createGuest(guestDTOs).getId())).build();
    }

    @GetMapping(value = "/get/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<GuestDTOs> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(guestService.readGuestById(id));
    }

    @GetMapping(value = "/get", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<GuestDTOs>> getByName(@PathParam("name") String name) {
        return ResponseEntity.ok(guestService.readGuestByName(name));
    }

    @GetMapping(value = "/getList", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<GuestDTOs>> getListAll() {
        return ResponseEntity.ok(guestService.readListAllGuest());
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> updateGuest(@PathVariable("id") Long id, @RequestBody GuestDTOs guestDTOs) {
        return ResponseEntity.created(locationByEntity(guestService.updateGuest(id, guestDTOs).getId())).build();
    }

    @DeleteMapping(value = "/delete/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteGuest(@PathVariable("id") Long id) {
        guestService.deleteGuest(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<String>("Guest deleted.", responseHeaders, HttpStatus.NO_CONTENT);
    }
}
