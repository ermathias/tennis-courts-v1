package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@Api(value="guestsetup", description="Tennis Court Admin operation for guest setup")
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @ApiOperation(value = "Add Guest For Tennis Court Reservation System")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully Added Guest")
    }
    )
    @RequestMapping(value="/addGuest",method = RequestMethod.POST,produces = "application/json")
//    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<Void> addGuest(GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestDTO).getId())).build();
    }

    @RequestMapping(value="/updateGuest",method = RequestMethod.PUT,produces = "application/json")
    public ResponseEntity<GuestDTO> updateGuest(GuestDTO guestDTO) {
        return ResponseEntity.ok(guestService.updateGuest(guestDTO));
    }

    @RequestMapping(value="/deleteGuest",method = RequestMethod.DELETE,produces = "application/json")
    public ResponseEntity<Long> deleteGuest(Long guestId) {
        return ResponseEntity.ok(guestService.deleteGuestById(guestId));
    }

    @RequestMapping(value="/findGuestById",method = RequestMethod.GET,produces = "application/json")
    public ResponseEntity<GuestDTO> findGuestById(Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @RequestMapping(value="/findGuestByName",method = RequestMethod.GET,produces = "application/json")
    public ResponseEntity<List<Guest>> findGuestByName(String guestName) {
        return ResponseEntity.ok(guestService.findGuestByName(guestName));
    }

    @RequestMapping(value="/findAllGuest",method = RequestMethod.GET,produces = "application/json")
    public ResponseEntity<List<Guest>> findAllGuest() {
        return ResponseEntity.ok(guestService.findAllGuest());
    }
}