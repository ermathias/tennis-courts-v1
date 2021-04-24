package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.exceptions.AdminConstraint;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("guest")
@AllArgsConstructor
@Validated
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @ApiOperation("Add a guest")
    @PostMapping(value = "addGuest")
    public ResponseEntity<Void> addGuest(@RequestHeader(defaultValue = "true", required = false) @AdminConstraint String isAdmin, String nameGuest) {
        // @formatter:off
        return ResponseEntity.created(
                locationByEntity(guestService.addGuest(nameGuest).getId()))
                .build();
        // @formatter:on
    }
    @ApiOperation("Delete a guest")
    @DeleteMapping(value = "deleteGuest")
    public ResponseEntity<Void> deleteGuest(@RequestHeader(defaultValue = "true", required = false) @AdminConstraint String isAdmin, Long guestId) {
        // @formatter:off
        guestService.deleteGuest(guestId);
        return ResponseEntity.noContent().build();
        // @formatter:on
    }

    @ApiOperation("Update a guest")
    @PutMapping(value = "updateGuest")
    public ResponseEntity<Void> updateGuest(@RequestHeader(defaultValue = "true", required = false) @AdminConstraint String isAdmin, GuestDTO guestDTO) {
        // @formatter:off
        return ResponseEntity.created(
                locationByEntity(guestService.updateGuest(guestDTO).getId()))
                .build();
        // @formatter:on
    }

    @ApiOperation("Lists all guests")
    @GetMapping(value = "listAllGuests")
    public ResponseEntity<List<GuestDTO>> listAllGuests(@RequestHeader(defaultValue = "true", required = false) @AdminConstraint String isAdmin) {
        // @formatter:off
        return ResponseEntity.ok(guestService.listAllGuests());
        // @formatter:on
    }

    @ApiOperation("Find guest by ID")
    @GetMapping(value = "findByGuestId")
    public ResponseEntity<GuestDTO> findByGuestId(@RequestHeader(defaultValue = "true", required = false) @AdminConstraint String isAdmin, Long guestId) {
        return ResponseEntity.ok(guestService.findByGuestId(guestId));
    }

    @ApiOperation("Find guest by Name")
    @GetMapping(value = "findByGuestName")
    public ResponseEntity<GuestDTO> findByGuestId(@RequestHeader(defaultValue = "true", required = false) @AdminConstraint String isAdmin, String guestName) {
        return ResponseEntity.ok(guestService.findByGuestName(guestName));
    }
}
