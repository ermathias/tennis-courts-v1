package com.tenniscourts.guests;

import com.tenniscourts.UriConstants;
import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.glassfish.jersey.server.Uri;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = UriConstants.GUEST_PATH)
public class GuestController extends BaseRestController {

    @GetMapping(path = UriConstants.ALL_PATH)
    @ApiOperation("Find all guests")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
    public ResponseEntity<List<GuestDTO>> findAllGuests() {
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = UriConstants.GUEST_ID_VARIABLE)
    @ApiOperation("Find an existing guest by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable String guestId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = UriConstants.NAME_PATH + UriConstants.GUEST_NAME_VARIABLE)
    @ApiOperation("Find an existing guest by name")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
    public ResponseEntity<List<GuestDTO>> findGuestByName(@PathVariable String guestName) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @ApiOperation("Create a new guest")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Guest created successfully") })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createGuest(@RequestBody @Valid CreateGuestRequestDTO createGuestRequestDTO) {
        return ResponseEntity.created(locationByEntity(1L)).build();
    }

    @PutMapping(path = UriConstants.GUEST_ID_VARIABLE + UriConstants.GUEST_NAME_VARIABLE)
    @ApiOperation("Update an existing guest")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Guest updated successfully") })
    public ResponseEntity<Void> updateGuest(@PathVariable Long guestId, @PathVariable String guestName) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = UriConstants.GUEST_ID_VARIABLE)
    @ApiOperation("Delete an existing guest")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Guest deleted successfully") })
    public ResponseEntity<Void> deleteGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok().build();
    }
}
