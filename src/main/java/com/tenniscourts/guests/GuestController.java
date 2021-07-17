package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @RequestMapping(value = "/add-guest", method = RequestMethod.POST)
    public ResponseEntity<Void> addGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestDTO).getId())).build();
    }

    @RequestMapping(value = "/update-guest", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<GuestDTO> updateGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.ok(guestService.updateGuest(guestDTO));
    }

    @RequestMapping(value = "/delete-guest", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Boolean> deleteGuest(@RequestParam("guestId") Long guestId) {
        return ResponseEntity.ok(guestService.deleteGuest(guestId));
    }

    @RequestMapping(value = "/find-guest-by-id", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GuestDTO> findGuestById(@RequestParam("guestId") Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @RequestMapping(value = "/find-guest-by-name", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GuestDTO> findGuestByName(@RequestParam("guestName") String guestName) {
        return ResponseEntity.ok(guestService.findGuestByName(guestName));
    }

    @RequestMapping(value = "/list-all-guests", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<GuestDTO>> listAllGuests() {
        return ResponseEntity.ok(guestService.listAllGuests());
    }
}
