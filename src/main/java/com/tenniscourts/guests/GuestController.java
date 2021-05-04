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
    public ResponseEntity<Void> createGuest(@RequestBody GuestRequestDTO guestRequestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestRequestDTO).getId())).build();
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> updateGuest(@PathVariable("id") Long id, @RequestBody GuestRequestDTO guestRequestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.updateGuest(id,guestRequestDTO).getId())).build();
    }

    @DeleteMapping(value = "/delete/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteGuest(@PathVariable("id") Long id) {
        guestService.deleteGuest(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<String>("Guest deleted.", responseHeaders, HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/find/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<GuestRequestDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(guestService.findGuestById(id));
    }

    @GetMapping(value = "/find", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<GuestRequestDTO>> findByName(@PathParam("name") String name) {
        return ResponseEntity.ok(guestService.findGuestByNmae(name));
    }

    @GetMapping(value = "/list", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<GuestRequestDTO>>  listAll() {
        return ResponseEntity.ok(guestService.listAllGuest());
    }
}
