package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @ApiOperation(value = "Insert new Guest by a Tennis Court Admin User. Parameter UserId is for checking user role")
    @PostMapping(value = "/insertGuest")
    public ResponseEntity<Void> addGuest(Long userId, String guestName, boolean isAdmin) {
        GuestDTO guest = new GuestDTO();
        guest.setName(guestName);
        guest.setAdmin(isAdmin);
        return ResponseEntity.created(locationByEntity(guestService.addGuest(userId, guest).getId())).build();

    }
    @ApiOperation(value = "Update a Guest by a Tennis Court Admin User. Parameter UserId is for checking user role")
    @PutMapping(value = "/updateGuest")
    public ResponseEntity<Void> updateGuest(Long userId, Long guestId, String guestName) {
        GuestDTO guest = guestService.findGuestById(guestId);
        return ResponseEntity.created(locationByEntity(guestService.updateGuest(userId, guest).getId())).build();

    }

    @ApiOperation(value = "Find guest by Id")
    @GetMapping(value = "/guestById")
    public ResponseEntity<GuestDTO> findGuestById(Long id){
        return ResponseEntity.ok(guestService.findGuestById(id));
    }

    @ApiOperation(value = "Find guest by name. Parameter UserId is for checking user role")
    @GetMapping(value = "/findGuestByName")
    public ResponseEntity<List<GuestDTO>> guestByName(Long userId, String name){
        return ResponseEntity.ok(guestService.findGuestsByName(userId, name));
    }

    @ApiOperation(value = "List all guests. Parameter UserId is for checking user role")
    @GetMapping(value = "/findAllGuests")
    public ResponseEntity<List<GuestDTO>> listAllGuests(Long id){
        return ResponseEntity.ok(guestService.findAllGuests(id));
    }

    @ApiOperation(value = "Delete guest with guestId. Parameter UserId is for checking user role")
    @PostMapping(value = "/deleteGuest")
    public ResponseEntity<Void> deleteGuest(Long userId, Long guestId){
        guestService.deleteGuest(userId, guestId);
        return ResponseEntity.noContent().build();
    }
}
