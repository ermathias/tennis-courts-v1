package com.tenniscourts.reservations;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class ReservationController extends BaseRestController {

	@Autowired
	private final ReservationService reservationService;

	@ApiOperation("Book reservation")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Reservation completed") })
	@PostMapping("/bookReservation")
	public ResponseEntity<Void> bookReservation(
			@Valid @RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
		return ResponseEntity
				.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId()))
				.build();
	}

	@ApiOperation("Find a reservation")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched reservation details") })
	@GetMapping("/findReservation{reservationId}")
	public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) {
		return ResponseEntity.ok(reservationService.findReservation(reservationId));
	}

	@ApiOperation("Cancel Reservation")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully canceled Reservation") })
	@PostMapping("cancel/reservation")
	public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
		return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
	}

	@ApiOperation("Reschedule Reservation")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully rescheduled Reservation") })
	@PostMapping("reschedule/reservation/{reservationId}/schedule/{scheduleId}")
	public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable Long reservationId,
			@PathVariable Long scheduleId) {
		return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
	}
}
