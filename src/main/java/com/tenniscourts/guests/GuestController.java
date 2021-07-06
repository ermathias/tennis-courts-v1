package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/guest")
public class GuestController extends BaseRestController {

    private GuestService guestService;

    @ApiOperation(value = "Add a new Guest")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Guest created")})
    @PostMapping
    public ResponseEntity<GuestDTO> addGuest(@Valid @RequestBody GuestDTO guest) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guest).getId())).build();
    }

    @ApiOperation(value = "Find Guest by ID")
    @GetMapping(value = "/{guestId}")
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @GetMapping(value = "/search-name/{guestName}")
    public ResponseEntity<GuestDTO> findGuestByName(@PathVariable String guestName) {
        return ResponseEntity.ok(guestService.findGuestByName(guestName));
    }


    @ApiOperation(value = "Find All Guests")
    @GetMapping
    public ResponseEntity<List<GuestDTO>> findAllGuests(){
       List<GuestDTO> guests = guestService.findAllGuests();
        return ResponseEntity.ok().body(guests);
    }

    @ApiOperation(value = "Delete Guest by ID")
    @DeleteMapping(value = "/{guestId}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long guestId){
        guestService.deleteGuest(guestId);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Update Guest")
    @PutMapping(value = "/{guestId}")
    public ResponseEntity<Void> updateGuest(@PathVariable Long guestId, @RequestBody GuestDTO guest ){
        guest.setId(guestId);
        guestService.updateGuest(guest);
        return ResponseEntity.noContent().build();
    }


}
