package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@AllArgsConstructor
public class ReservationController extends BaseRestController {
	private  ReservationService reservationService;
@PostMapping
	public ResponseEntity<Void> bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
		return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
	}
@GetMapping
	public ResponseEntity<ReservationDTO> findReservation(Long reservationId) {
		return ResponseEntity.ok(reservationService.findReservation(reservationId));
	}
@DeleteMapping
	public ResponseEntity<ReservationDTO> cancelReservation(Long reservationId) {
		return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
	}
@PutMapping
	public ResponseEntity<ReservationDTO> rescheduleReservation(Long reservationId, Long scheduleId) {
		return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
	}
}
