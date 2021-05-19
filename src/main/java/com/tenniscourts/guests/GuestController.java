package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/guests")
public class GuestController extends BaseRestController {

    @Autowired
    private final GuestService guestService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<GuestDTO> addGuest(@Valid @RequestBody NewGuestDTO newGuestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(newGuestDTO).getId())).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<GuestDTO>> listGuests() {
        return ResponseEntity.ok(guestService.listAllGuests());
    }

    @RequestMapping(value = "/id/{guestId}", method = RequestMethod.GET)
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @RequestMapping(value = "/name/{guestName}", method = RequestMethod.GET)
    public ResponseEntity<GuestDTO> findGuestByName(@PathVariable String guestName) {
        return ResponseEntity.ok(guestService.findGuestByName(guestName));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<GuestDTO> updateGuest(@Valid @RequestBody GuestDTO guestDTO) {
        return ResponseEntity.ok(guestService.updateGuest(guestDTO));
    }

    @RequestMapping(value = "/{guestId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteGuest(@PathVariable Long guestId) {
        guestService.deleteGuest(guestId);
        return ResponseEntity.ok().build();
    }
}
