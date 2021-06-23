package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.schedules.CreateScheduleRequestDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/guest")
@Api(value = "Guest Controller")
public class GuestController extends BaseRestController {

    @Autowired
    private final GuestService guestService;

    @PostMapping("/add")
    @ApiOperation(value = "Add guest")
    public ResponseEntity<Void> addGuest(@RequestBody CreateGuestRequestDTO createGuestRequestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(createGuestRequestDTO).getId())).build();
    }

    @PutMapping("/update")
    @ApiOperation(value = "Update guest")
    public ResponseEntity<Void> updateGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.updateGuest(guestDTO).getId())).build();
    }

    @GetMapping("/findAll")
    @ApiOperation(value = "Find all guests")
    public ResponseEntity<List<GuestDTO>> findAllGuests() {
        return ResponseEntity.ok(guestService.findAllGuests());
    }

    @GetMapping("/find/id/{guestId}")
    @ApiOperation(value = "Find guest by id")
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @GetMapping("/find/name/{guestName}")
    @ApiOperation(value = "Find guest by name")
    public ResponseEntity<GuestDTO> findGuestByName(@PathVariable String guestName) {
        return ResponseEntity.ok(guestService.findGuestByName(guestName));
    }

    @DeleteMapping("/remove/{guestId}")
    @ApiOperation(value = "Delete guest by id")
    public void deleteGuestById(@PathVariable Long guestId) {
        guestService.deleteGuestById(guestId);
    }
}
