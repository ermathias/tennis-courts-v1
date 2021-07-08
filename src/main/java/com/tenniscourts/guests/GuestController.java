package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api("Guest")
@AllArgsConstructor
@RestController
@RequestMapping("/guest")
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @PostMapping(value = "/add")
    @ApiOperation(value="Add a Guest")
    @ApiResponse(code = 201, message = "Guest created")
    public ResponseEntity<Void> addGuest(@Valid @RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestDTO).getId())).build();
    }

    @GetMapping(value = "/find/{guestId}")
    @ApiOperation(value="Find a Guest by ID")
    @ApiResponse(code = 200, message = "Guest found")
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable("guestId") Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @GetMapping(value = "/find")
    @ApiOperation(value="Find all Guests")
    @ApiResponse(code = 200, message = "Tennis Court with schedule found")
    public ResponseEntity<List<GuestDTO>> findAllGuests() {
        return ResponseEntity.ok(guestService.findAllGuests());
    }

    @GetMapping(value = "/search", params = "guestName")
    @ApiOperation(value="Find Guest(s) by name")
    @ApiResponse(code = 200, message = "Guest(s) found")
    public ResponseEntity<List<GuestDTO>> findGuestByName(String guestName) {
        return ResponseEntity.ok(guestService.findGuestByName(guestName));
    }

    @PutMapping(value = "/update/{guestId}")
    @ApiOperation(value="Update a Guest")
    @ApiResponse(code = 200, message = "Guest updated")
    public ResponseEntity<GuestDTO> updateGuest(@Valid @RequestBody GuestDTO guestDTO) {
        return ResponseEntity.ok(guestService.updateGuest(guestDTO));
    }

    @DeleteMapping(value = "/remove/{guestId}")
    @ApiOperation(value="Find a Guest by ID")
    @ApiResponse(code = 204, message = "Guest removed")
    public ResponseEntity<Void> removeGuest(@PathVariable("guestId") Long guestId) {
        guestService.deleteGuestById(guestId);
        return ResponseEntity.noContent().build();
    }
}
