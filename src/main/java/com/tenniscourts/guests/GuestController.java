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
@Api(value = "GuestController")
@RequestMapping("/guests")
@RestController
public class GuestController extends BaseRestController {

    @Autowired
    private final GuestService guestService;

    //TODO: implement rest and swagger
    @ApiOperation(value = "Add a guest", tags = "addGuest")
    @PostMapping("/addGuest")
    public ResponseEntity<Void> addGuest(@Valid @RequestBody GuestDTO guestDto) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestDto).getId())).build();
    }

    //TODO: implement rest and swagger
    @ApiOperation(value = "Find a guest by id", tags = "findGuestById")
    @GetMapping("/findGuestById/{guestId}")
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    //TODO: implement rest and swagger
    @ApiOperation(value = "Find a guest by name", tags = "findGuestByName")
    @GetMapping("/findGuestByName/{name}")
    public ResponseEntity<GuestDTO> findGuestByName(@PathVariable String name) {
        return ResponseEntity.ok(guestService.findGuestByName(name));
    }

    //TODO: implement rest and swagger
    @ApiOperation(value = "List all guests", tags = "listAllGuests")
    @GetMapping("/listAllGuests")
    public ResponseEntity<List<GuestDTO>> listAllGuests() {
        return ResponseEntity.ok(guestService.findAllGuests());
    }

    //TODO: implement rest and swagger
    @ApiOperation(value = "Update a guest", tags = "updateGuest")
    @PutMapping("/updateGuest")
    public ResponseEntity<GuestDTO> updateGuest(@Valid @RequestBody GuestDTO guestDTO) {
        return ResponseEntity.ok(guestService.updateGuest(guestDTO));
    }

    //TODO: implement rest and swagger
    @ApiOperation(value = "Delete a guest", tags = "deleteGuest")
    @DeleteMapping("/deleteGuest/{guestId}")
    public ResponseEntity<GuestDTO> deleteGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.deleteGuest(guestId));
    }

}
