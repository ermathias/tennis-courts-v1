package com.tenniscourts.reservations;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "", tags = "Reservations", description = "Reservations REST Endpoints")
@RequestMapping(value = "/reservations")
@Validated
public interface ReservationAPIService {
	
	@PostMapping(value = "/bulk", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Book one or more reservation", nickname = "bookReservations")
	default ResponseEntity<List<Long>> bookReservations(
			@ApiParam(value = "Reservations data") @Valid @RequestBody List<CreateReservationRequestDTO> reservationsRequestDTO) {
		throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Book reservation", nickname = "bookReservation")
	default ResponseEntity<Void> bookReservation(
			@ApiParam @Valid @RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
		throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

	@GetMapping(value = "/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Find reservation", nickname = "findReservation")
	default ResponseEntity<ReservationDTO> findReservation(
			@PathVariable Long reservationId) {
		throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

	@PatchMapping(value = "/{reservationId}/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Cancel reservation", nickname = "cancelReservation")
	default ResponseEntity<ReservationDTO> cancelReservation(
			@PathVariable Long reservationId) {
		throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

	@PatchMapping(value = "/{reservationId}/reschedule/{scheduleId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Reschedule reservation", nickname = "rescheduleReservation")
	default ResponseEntity<ReservationDTO> rescheduleReservation(
			@PathVariable Long reservationId, 
			@PathVariable Long scheduleId) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

}