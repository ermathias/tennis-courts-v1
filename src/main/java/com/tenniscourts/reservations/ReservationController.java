package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/reservation")
public class ReservationController extends BaseRestController {
		@Autowired
    private final ReservationService reservationService;

		@ApiResponses(value = { 
				@ApiResponse(code = 201, message = "Reservation Created") 
		})
		@ApiOperation("Book Reservation") 
		@PostMapping("")
    public ResponseEntity<Void> bookReservation(
    		@Valid @RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

		@ApiOperation("List all past Reservations")
		@GetMapping("/listPastReservations")
		public ResponseEntity<List<ReservationDTO>> findAlPastReservations(){
			return ResponseEntity.ok(reservationService.findAllPastReservations());
		}
	
		@ApiOperation("Find Reservation")
		@GetMapping("/{reservationId}")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }
	
		@ApiOperation("Cancel Reservation")
		@DeleteMapping("/{reservationId}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

		@ApiOperation("Reschedule Reservation")
		@PutMapping("/{reservationId}/{scheduleId}")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable Long reservationId,
    		@PathVariable Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}



