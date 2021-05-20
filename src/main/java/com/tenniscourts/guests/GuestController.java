package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Api(value = "GuestController", description = "Operations pertaining to guests")
@RestController
@RequestMapping("/guest")
public class GuestController extends BaseRestController {

    @Autowired
    private GuestService guestService;

    @ApiOperation(value = "Add guest", response = ResponseEntity.class, tags = "Add guest")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Void> addGuest(@RequestBody GuestDTO guest) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guest).getId())).build();
    }

    @ApiOperation(value = "Update guest", response = ResponseEntity.class, tags = "Update guest")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<GuestDTO> updateGuest(@RequestBody GuestDTO guest) {
        return ResponseEntity.created(locationByEntity(guestService.updateGuest(guest).getId())).build();
    }

    @ApiOperation(value = "Find guest by Id", response = ResponseEntity.class, tags = "Find guest by Id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(guestService.findGuestById(id));
    }

    @ApiOperation(value = "Delete by Id", response = ResponseEntity.class, tags = "Delete by Id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<List<GuestDTO>> deleteGuestById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(guestService.deleteGuest(id));
    }

    @ApiOperation(value = "Find guest by Name", response = ResponseEntity.class, tags = "Find guest by Name")
    @RequestMapping(value = "/findByName/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<GuestDTO>> findGuestByName(@PathVariable(value = "name") String name) {
        return ResponseEntity.ok(guestService.findGuestByName(name));
    }

    @ApiOperation(value = "List all the guests", response = ResponseEntity.class, tags = "List all the guests")
    @RequestMapping(value = "/fetchAll", method = RequestMethod.GET)
    public ResponseEntity<List<GuestDTO>> fetchAllGuests() {
        return ResponseEntity.ok(guestService.fetchAll());
    }
}
