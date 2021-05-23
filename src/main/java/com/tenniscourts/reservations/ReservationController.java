package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ReservationController extends BaseRestController {

	@Autowired
    private ReservationService reservationService;

	@PostMapping(value = "/bookReservation")
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }
	
	/*@PostMapping(value = "/bookReservations")
    public ResponseEntity<Void> bookReservations(@RequestBody List<CreateReservationRequestDTO> createReservationRequestDTOList) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservations(createReservationRequestDTOList).iterator().next().getId())).build();
    }*/

	@GetMapping(value = "/getReservation")
    public ResponseEntity<ReservationDTO> findReservation(@RequestBody Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

	@DeleteMapping(value = "/cancelReservation")
    public ResponseEntity<ReservationDTO> cancelReservation(@RequestBody Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

	@PostMapping(value = "/rescheduleReservation")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@RequestBody Long reservationId, @RequestBody Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
