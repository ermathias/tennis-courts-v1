package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/guests")
@AllArgsConstructor
public class GuestController extends BaseRestController {

    @Autowired
    private final GuestService guestService;

    @GetMapping
    @ApiOperation("Find all Guests")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<List<GuestDTO>> findAllGuests(Pageable pageable) {
        return ResponseEntity.ok(guestService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @ApiOperation("Find a Guest by ID")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(guestService.findById(id));
    }

    @GetMapping("/name/{name}")
    @ApiOperation("Find a Guest by name")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<List<GuestDTO>> findGuestByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(guestService.findByName(name));
    }

    @PostMapping
    @ApiOperation("Create a new Guest")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Guest successfully created")})
    public ResponseEntity<GuestDTO> insertGuest(@Valid @RequestBody GuestDTO dto) {
        return ResponseEntity.created(locationByEntity(guestService.insert(dto).getId())).build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a Guest by ID")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Guest successfully deleted")})
    public void deleteGuest(@PathVariable("id") Long id) {
        guestService.delete(id);
    }

}
