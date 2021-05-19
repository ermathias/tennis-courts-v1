package com.tenniscourts.guests;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.reservations.CreateReservationRequestDTO;
import com.tenniscourts.reservations.ReservationDTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController("/guests")
public class GuestController {
	 private final GuestService guestService;

//	    @PostMapping("/guest")
//	    public ResponseEntity<Void> createGuest(CreateReservationRequestDTO createReservationRequestDTO) throws Exception{
//	        return ResponseEntity.created(guestService.createGuest(createReservationRequestDTO).getId())).build();
//	    }
//
//
//	    @GetMapping("/guest/{id}")
//	    public ResponseEntity<GuestDTO> findGuestbyId(Long guestId) throws Exception{
//	        return ResponseEntity.ok(guestService.findReservation(reservationId));
//	    }
//	    
//	    @GetMapping("/guest/{name}")
//	    public ResponseEntity<ReservationDTO> findGuestByName(String reservationId) throws Exception{
//	        return ResponseEntity.ok(guestService.findReservation(reservationId));
//	    }
//	    
//	    @GetMapping("/guests")
//	    public ResponseEntity<ReservationDTO> getAllGuests() throws Exception{
//	        return ResponseEntity.ok(guestService.findReservation(reservationId));
//	    }
//
//	    @DeleteMapping("/guest")
//	    public ResponseEntity<ReservationDTO> deleteGuest(Long reservationId) throws Exception{
//	        return ResponseEntity.ok(guestService.cancelReservation(reservationId));
//	    }
//	    
//	    @PutMapping("/guest")
//	    public ResponseEntity<ReservationDTO> updateGuest(Long reservationId) throws Exception{
//	        return ResponseEntity.ok(guestService.cancelReservation(reservationId));
//	    }

}
