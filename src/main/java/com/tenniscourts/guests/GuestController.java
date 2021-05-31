package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Api(value = "Guests Controller")
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @PostMapping("/guest")
    @ApiOperation(value = "Add a guest")
    @ApiResponse(code = 201, message = "Created")
    public ResponseEntity<Void> addGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestDTO).getId())).build();
    }

    @PutMapping("/guest")
    @ApiOperation(value = "Update a guest", response = GuestDTO.class)
    @ApiResponse(code = 200, message = "OK")
    public ResponseEntity<GuestDTO> updateGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.ok(guestService.updateGuest(guestDTO));
    }

    @GetMapping("/guests")
    @ApiOperation(value = "Find all guests", response = Iterable.class)
    @ApiResponse(code = 200, message = "OK")
    public ResponseEntity<List<GuestDTO>> findAll() {
        return ResponseEntity.ok(guestService.findAll());
    }

    @GetMapping("/guest/{guestId}")
    @ApiOperation(value = "Find a guest by id", response = GuestDTO.class)
    @ApiResponse(code = 200, message = "OK")
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @GetMapping("/guest")
    @ApiOperation(value = "Find a guest by name", response = GuestDTO.class)
    @ApiResponse(code = 200, message = "OK")
    public ResponseEntity<GuestDTO> findGuestByName(@RequestParam String guestName) {
        return ResponseEntity.ok(guestService.findGuestByName(guestName));
    }

    @DeleteMapping("/guest/{guestId}")
    @ApiOperation("Removes a guest by id")
    @ApiResponse(code = 200, message = "OK")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long guestId) {
        guestService.deleteById(guestId);
        return ResponseEntity.ok().build();
    }
}
