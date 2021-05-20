package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
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
@Api(tags = {"Guest API"})
public class GuestController extends BaseRestController {

    @Autowired
    private final GuestService guestService;

    @ApiOperation("Add a new guest to the system")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<GuestDTO> addGuest(@Valid @RequestBody NewGuestDTO newGuestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(newGuestDTO).getId())).build();
    }

    @ApiOperation("List all guests in the system")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<GuestDTO>> listGuests() {
        return ResponseEntity.ok(guestService.listAllGuests());
    }

    @ApiOperation("Get a guest in the system with a defined ID")
    @RequestMapping(value = "/id/{guestId}", method = RequestMethod.GET)
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @ApiOperation("Get a guest in the system with a defined name")
    @RequestMapping(value = "/name/{guestName}", method = RequestMethod.GET)
    public ResponseEntity<GuestDTO> findGuestByName(@PathVariable String guestName) {
        return ResponseEntity.ok(guestService.findGuestByName(guestName));
    }

    @ApiOperation("Updates the data from a guest in the system, if the defined ID is not found a new guest will be added")
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<GuestDTO> updateGuest(@Valid @RequestBody GuestDTO guestDTO) {
        return ResponseEntity.ok(guestService.updateGuest(guestDTO));
    }

    @ApiOperation("Deletes a guest in the system with a defined ID")
    @RequestMapping(value = "/{guestId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteGuest(@PathVariable Long guestId) {
        guestService.deleteGuest(guestId);
        return ResponseEntity.ok().build();
    }
}
