package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/guests")
public class GuestController extends BaseRestController {

    @Autowired
    private final GuestService guestService;

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok" )})
    @ApiOperation("Fetch all the guests")
    @GetMapping
    public List<GuestDTO> getAllGuests(){
        return guestService.getAllGuests();
    }

    @PostMapping
    @ApiOperation("Saves a guest to the database")
    @ApiResponses(value = { @ApiResponse( code = 201, message = "Guest saved" ) } )
    public ResponseEntity<Void> saveGuest(@Valid @RequestBody GuestDTO guestDTO ) {
        return ResponseEntity.created(locationByEntity(guestService.saveGuest(guestDTO).getId())).build();
    }

}
