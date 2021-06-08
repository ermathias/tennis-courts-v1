package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guests")
@AllArgsConstructor
public class GuestController extends BaseRestController {
    private final GuestService guestService;

    @PostMapping
    public ResponseEntity<Void> addGuest(@RequestBody CreateGuestRequestDTO guestRequestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestRequestDTO).getId())).build();
    }

    @GetMapping("/{guestId}")
    public ResponseEntity<GuestDTO> findGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.findGuest(guestId));
    }

    @GetMapping()
    public ResponseEntity<List<GuestDTO>> findAllGuest(@RequestParam(required = false) String namePattern) {
        if (StringUtils.isEmpty(namePattern)) {
            return ResponseEntity.ok(guestService.findAllGuest());
        }
        return ResponseEntity.ok(guestService.findGuestByName(namePattern));
    }

    @DeleteMapping("/{guestId}")
    public ResponseEntity<?> removeGuest(@PathVariable Long guestId) {
        guestService.removeGuest(guestId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{guestId}")
    public ResponseEntity<GuestDTO> updateGuest(
            @PathVariable Long guestId, @RequestBody CreateGuestRequestDTO guestRequestDTO
    ) {
        return ResponseEntity.ok(guestService.updateGuest(guestId, guestRequestDTO));
    }
}
