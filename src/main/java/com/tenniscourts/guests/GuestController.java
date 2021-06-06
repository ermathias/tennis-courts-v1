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
    @ApiOperation(value = "Create guest for  Tennis Court", notes = "Guest for  Tennis Court")
    public ResponseEntity<Void> addGuest(@Valid @RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestDTO).getId())).build();
    }

    @GetMapping
    @ApiOperation(value = "Fetchingg All guest", notes = "Find all Guests")
      public Iterable<GuestDTO> findAll(Pageable pageable) {
        return guestService.findAll(pageable);
    }

    @GetMapping("/{guestId}")
    @ApiOperation(value = "Fetchingg  guest by Id", notes = "Guest by id")
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable("guestId") Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @GetMapping("name/{name}")
    @ApiOperation(value = "Fetchingg by name", notes = "Guest name")
    public ResponseEntity<List<GuestDTO>> findGuestByName(@PathVariable("name") String guestName) {
        return ResponseEntity.ok(guestService.findGuestByName(guestName));
    }

    @DeleteMapping("/{guestId}")
    @ApiOperation(value = "Deleting guest by id", notes = "Guest delete by id")
    public void delete(@PathVariable("guestId") Long guestId) {
        this.guestService.deleteById(guestId);
    }
}