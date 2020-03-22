package com.tenniscourts.guests;

import java.util.List;

import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = {"Guests API"})
interface GuestAPI {

	@ApiOperation("Registers a guest.")
    public ResponseEntity<Void> createGuest(@ApiParam("Guest information.") CreateGuestRequestDTO createGuestRequestDTO);

	@ApiOperation("Updates a guest by its id.")
	@ApiResponses({@ApiResponse(code = 404, message = "Guest not found.")})
    public ResponseEntity<GuestDTO> updateGuestName(@ApiParam(value = "Guest id.", example = "1") Long guestId, 
    											@ApiParam(value = "Guest name.", example = "Roger Federer") String name);
	@ApiOperation("Finds a guest by its id.")
	@ApiResponses({@ApiResponse(code = 404, message = "Guest not found.")})
    public ResponseEntity<GuestDTO> findGuestById(@ApiParam(value = "Guest id.", example = "1") Long guestId);

	@ApiOperation("Finds a guest by its name.")
	@ApiResponses({@ApiResponse(code = 404, message = "Guest not found.")})
    public ResponseEntity<GuestDTO> findGuestByName(@ApiParam(value = "Guest name.", example = "Roger Federer") String name);

	@ApiOperation("Finds all guests.")
    public ResponseEntity<List<GuestDTO>> findAll();

	@ApiOperation("Deletes a guest by its id.")
	@ApiResponses({@ApiResponse(code = 404, message = "Guest not found.")})
    public ResponseEntity<Void> deleteGuest(@ApiParam(value = "Guest id.", example = "1") Long guestId);
}
