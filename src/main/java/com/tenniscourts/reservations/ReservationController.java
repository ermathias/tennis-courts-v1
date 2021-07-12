package com.tenniscourts.reservations;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ReservationController extends BaseRestController {

	private final ReservationService reservationService;

	@PostMapping(value = "/v1/reservation/book")
	public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
		
		return ResponseEntity
				.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId()))
				.build();
		
	}

	@GetMapping(value = "/v1/reservation/retrieve")
	public ResponseEntity<List<ReservationDTO>> findReservations() {
		
		return ResponseEntity.ok(reservationService.findReservations());
		
	}

	@GetMapping(value = "/v1/reservation/{reservationId}/retrieve")
	public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) {
		
		return ResponseEntity.ok(reservationService.findReservation(reservationId));
		
	}

	@DeleteMapping(value = "/v1/reservation/{reservationId}/cancel")
	public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
		
		return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
		
	}

	@PostMapping(value = "/v1/reservation/{reservationId}/{scheduleId}/reschedule")
	public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable Long reservationId, @PathVariable Long scheduleId) {
		
		return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
		
	}
}
