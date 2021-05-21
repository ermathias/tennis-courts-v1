package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/guests") // TODO admin secure path
public class GuestsController extends BaseRestController {
    private final GuestsService guestsService;

    @PostMapping
    @ApiOperation("Adds a new guest")
    public ResponseEntity<Void> addGuest(@RequestBody CreateGuestRequestDTO createGuestRequestDTO) {
        return ResponseEntity.created(locationByEntity(guestsService.addGuest(createGuestRequestDTO).getId())).build();
    }

    @PutMapping("/{guestId}")
    @ApiOperation("Updates guest's name")
    public ResponseEntity<GuestDTO> updateGuest(@PathVariable Long guestId, @RequestBody UpdateGuestRequestDTO updateGuestRequestDTO) {
        return ResponseEntity.ok(guestsService.updateGuest(guestId, updateGuestRequestDTO));
    }

    @DeleteMapping("/{guestId}")
    @ApiOperation("Removes a guest by id")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long guestId) {
        guestsService.deleteGuest(guestId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @ApiOperation("Fetches all guests")
    @ApiParam(name = "name", value = "The guest's name to filter")
    public ResponseEntity<List<GuestDTO>> findGuests(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(guestsService.findGuests(name));
    }

    @GetMapping("/{guestId}")
    @ApiOperation("Fetches a guest by id")
    public ResponseEntity<GuestDTO> findGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestsService.findGuest(guestId));
    }

}
