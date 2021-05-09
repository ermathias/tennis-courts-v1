package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/guests")
@AllArgsConstructor
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @ApiOperation(value = "Creates a new guest")
    @PostMapping
    private ResponseEntity<Void> createGuest(@RequestBody CreateGuestDTO createGuestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.createGuest(createGuestDTO).getId())).build();
    }

    @ApiOperation(value = "Returns a guest of a given id")
    @GetMapping("/{guestId}")
    private ResponseEntity<GuestDTO> findGuestById(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @ApiOperation(value = "Returns a list of guests by a given name")
    @GetMapping("/name/{guestName}")
    private ResponseEntity<List<GuestDTO>> findGuestsByName(@PathVariable String guestName) {
        return ResponseEntity.ok(guestService.findGuestsByName(guestName));
    }

    @ApiOperation(value = "Updates a guest name")
    @PutMapping("/{guestId}")
    private ResponseEntity<Void> updateGuest(@PathVariable Long guestId, @RequestBody CreateGuestDTO createGuestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.updateGuest(guestId, createGuestDTO).getId())).build();
    }

    @ApiOperation(value = "Deletes a guest from database")
    @DeleteMapping("/{guestId}")
    private ResponseEntity<GuestDTO> deleteGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.deleteGuest(guestId));
    }

    @ApiOperation(value = "List guests paged")
    @GetMapping("/paged")
    private ResponseEntity<Page<GuestDTO>> getGuests(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return ResponseEntity.ok(guestService.getGuestsPaged(page, pageSize));
    }

    @ApiOperation(value = "List all guests")
    @GetMapping
    private ResponseEntity<List<GuestDTO>> getGuests() {
        return ResponseEntity.ok(guestService.listAllGuests());
    }
}
