package com.tenniscourts.guests;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.reservations.ReservationDTO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class GuestController extends BaseRestController {
	
	private final GuestService guestService;
	
	private final GuestMapper guestMapper;
	
	//Create/Update/Delete/Find by id/Find by name/List all the guests
	

	@PutMapping(value = "/guest")
	@ApiOperation(value = "Create new Guest")
    @ApiResponses(value = {
    	    @ApiResponse(code = 200, message = "Create new Guest")})
    @GetMapping(value = "/guest", produces = "application/json")
	public ResponseEntity<Void> createGuest(CreateGuestRequestDTO createGuestRequestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.createGuest(createGuestRequestDTO).getId())).build();
    }
	
	@ApiOperation(value = "Update Guest")
    @ApiResponses(value = {
    	    @ApiResponse(code = 200, message = "Update Guest")})
	@PostMapping(value = "/guest/update/", produces = "application/json")
    public ResponseEntity<Guest> updateGuest(@RequestBody Guest guestDTO) {
        return ResponseEntity.ok(guestService.update(guestDTO));
    }
	
	@ApiOperation(value = "Delete Guest")
    @ApiResponses(value = {
    	    @ApiResponse(code = 404, message = "Delete Guest")})
	@DeleteMapping(value = "/guest/delete/{guestId}", produces = "application/json")
    public HeadersBuilder<?> deleteGuest(@PathVariable Long guestId) {
		guestService.delete(guestId);
        return ResponseEntity.notFound();
    }
	
	@GetMapping(value = "/guest/{guestId}", produces = "application/json")
    public ResponseEntity<Optional<Guest>> findGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.findById(guestId));
    }
	
	@GetMapping(value = "/guest/{guestId}", produces = "application/json")
    public ResponseEntity<List<Guest>> findGuestByName(@PathVariable String name) {
        return ResponseEntity.ok(guestService.findByName(name));
    }
	
	//TODO: implement rest and swagger
    @ApiOperation(value = "Returns a full guests list")
    @ApiResponses(value = {
    	    @ApiResponse(code = 200, message = "Returns the guests list")})
    @GetMapping(value = "/guests", produces = "application/json")
    public ResponseEntity<List<Guest>> listAll() {
        return ResponseEntity.ok(guestService.findAll());
    }

}
