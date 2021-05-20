package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.config.api.RestAPI;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestAPI
@AllArgsConstructor
public class GuestController extends BaseRestController implements GuestAPIService {

    private GuestService guestService;

    @Override
    public ResponseEntity<Void> addGuest(CreateGuestRequestDTO createGuestRequestDTO) {
        return ResponseEntity.created(locationByEntity(this.guestService.addGuest(createGuestRequestDTO).getId())).build();
    }

    @Override
    public ResponseEntity<GuestDTO> findGuestById(Long guestId) {
        return ResponseEntity.ok(this.guestService.findGuestById(guestId));
    }

    @Override
    public ResponseEntity<List<GuestDTO>> findGuestsByName(String guestName) {
        return ResponseEntity.ok(this.guestService.findGuestsByName(guestName));
    }

    @Override
    public ResponseEntity<Void> updateGuest(Long guestId, CreateGuestRequestDTO createGuestRequestDTO) {
        return ResponseEntity.created(locationByEntity(this.guestService.updateGuest(guestId, createGuestRequestDTO).getId())).build();
    }

    @Override
    public ResponseEntity<GuestDTO> deleteGuest(Long guestId) {
        return ResponseEntity.ok(this.guestService.deleteGuest(guestId));
    }

    @Override
    public ResponseEntity<List<GuestDTO>> getGuests() {
        return ResponseEntity.ok(this.guestService.getAllGuests());
    }

}
