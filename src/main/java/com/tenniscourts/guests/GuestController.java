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
@RestController
@RequestMapping(value = "/guests")
@CrossOrigin(value = "*")
@Api(value = "Guests API")
public class GuestController extends BaseRestController {

    @Autowired
    private final GuestService guestService;

    @Autowired
    private final GuestMapper guestMapper;

    @GetMapping
    @ApiOperation(value = "Returns a list containing all guests.")
    public ResponseEntity<List<GuestDTO>> listAllGuests() {
        return ResponseEntity.ok().body(guestMapper.map(guestService.listAllGuests()));
    }

    @GetMapping("/{guestId}")
    @ApiOperation(value = "Returns only one guest identified by its unique id.")
    public ResponseEntity<GuestDTO> findGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok().body(guestMapper.map(guestService.findGuest(guestId)));
    }


    @GetMapping("/find-by-name/{guestName}")
    @ApiOperation(value = "Returns a list of guests with corresponding names.")
    public ResponseEntity<List<GuestDTO>> findGuestByName(@PathVariable String guestName) {
        return ResponseEntity.ok().body(guestMapper.map(guestService.findGuestByName(guestName)));
    }

    @PostMapping
    @ApiOperation(value = "Creates a new guest entity.")
    public ResponseEntity<Void> addGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestDTO).getId())).build();
    }

    @PutMapping("/{guestId}")
    @ApiOperation(value = "Changes a specific guest's attributes based on the new data sent.")
    public ResponseEntity<GuestDTO> updateGuest(@PathVariable Long guestId, @RequestBody GuestDTO guestDTO) {
        return ResponseEntity.ok().body(guestMapper.map(guestService.updateGuest(guestId, guestDTO)));
    }

    @DeleteMapping("/{guestId}")
    @ApiOperation(value = "Deletes a specific guest entity.")
    public ResponseEntity<Void> removeGuest(@PathVariable Long guestId) {
        guestService.removeGuest(guestId);
        return ResponseEntity.noContent().build();
    }

}
