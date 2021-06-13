package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/guests")
public class GuestController extends BaseRestController {

    @Autowired
    private final GuestService guestService;

    @PostMapping
    @ApiOperation("Create a new guest")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Guest created with success") })
    public ResponseEntity<Void> addGuest(@Valid @RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestDTO).getId())).build();
    }

    @GetMapping
    @ApiOperation("Find all Guests")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok") })
    public Iterable<GuestDTO> findAll(Pageable pageable) {
        return guestService.findAll(pageable);
    }

    @GetMapping("/{guestId}")
    @ApiOperation("Find Guest by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok") })
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable("guestId") Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @GetMapping("name/{name}")
    @ApiOperation("Find Guest by name")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok") })
    public ResponseEntity<List<GuestDTO>> findGuestByName(@PathVariable("name") String guestName) {
        return ResponseEntity.ok(guestService.findGuestByName(guestName));
    }

    @DeleteMapping("/{guestId}")
    @ApiOperation("Delete Guest by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Guest deleted with sucess") })
    public void delete(@PathVariable("guestId") Long guestId) {
        this.guestService.deleteById(guestId);
    }
}