package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/guests")
@CrossOrigin(origins = "*")
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping
    @ApiOperation(value = "API operation that create a new guest.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Guest successfully created.")
    })
    public ResponseEntity<Void> addGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestDTO).getId())).build();
    }

    @GetMapping("/{guestId}")
    @ApiOperation(value = "API operation that return a guest by ID number.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Guest found."),
            @ApiResponse(code = 404, message = "Guest not found.")
    })
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @GetMapping("/name/{name}")
    @ApiOperation(value = "API operation that return all guests.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of guests found."),
            @ApiResponse(code = 404, message = "List of guests not found.")
    })
    public ResponseEntity<GuestDTO> findGuestByName(@PathVariable String name) {
        return ResponseEntity.ok(guestService.findGuestByName(name));
    }

    @GetMapping
    @ApiOperation(value = "API operation that return a guest by exactly name saved.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Guest found."),
            @ApiResponse(code = 404, message = "Guest not found.")
    })
    public ResponseEntity<List<GuestDTO>> findAllGuests() {
        return ResponseEntity.ok(guestService.findAll());
    }

    @PutMapping("/{guestId}")
    @ApiOperation(value = "API operation that update a guest by ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Guest successfully updated."),
            @ApiResponse(code = 404, message = "Guest not found.")
    })
    public ResponseEntity<GuestDTO> updateGuest(@PathVariable Long guestId, @RequestBody GuestDTO guestDTO) {
        return ResponseEntity.ok(guestService.updateGuest(guestDTO, guestId));
    }

    @DeleteMapping("/{guestId}")
    @ApiOperation(value = "API operation that delete a guest by ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Guest successfully deleted."),
            @ApiResponse(code = 404, message = "Guest not found.")
    })
    public ResponseEntity<GuestDTO> removeGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.removeGuest(guestId));
    }

}
