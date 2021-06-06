package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/guests")
@AllArgsConstructor
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @PostMapping(value = "/")
    @ApiOperation(value = "Create guest")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created guest")})
    public ResponseEntity<Void> addGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestDTO).getId())).build();
    }

    @GetMapping(value = "/{guestId}")
    @ApiOperation("Find guest by ID")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Find guest by ID - success")})
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @GetMapping(value = "/search")
    @ApiOperation("Search guest by name")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Find guest by name - success")})
    public ResponseEntity<List<GuestDTO>> findGuestByName(@RequestParam String guestName) {
        return ResponseEntity.ok(guestService.findGuestByName(guestName));
    }

    @GetMapping(value = "/")
    @ApiOperation("Find all guests")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Find all guests - success")})
    public ResponseEntity<List<GuestDTO>> findAllGuests() {
        return ResponseEntity.ok(guestService.findAllGuests());
    }

    @DeleteMapping(value = "/{guestId}")
    @ApiOperation("Delete guest")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Delete guest - success")})
    public ResponseEntity<Long> deleteGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.deleteGuestById(guestId));
    }

    @PutMapping(value = "/")
    @ApiOperation("Update guest")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Update guest - success")})
    public ResponseEntity<GuestDTO> updateGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.ok(guestService.updateGuest(guestDTO));
    }
}