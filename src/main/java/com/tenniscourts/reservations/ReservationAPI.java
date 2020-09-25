package com.tenniscourts.reservations;

import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Reservations API")
interface ReservationAPI {

	@ApiOperation("Books a reservation.")
    public ResponseEntity<Void> bookReservation(@ApiParam("Reservation information.") CreateReservationRequestDTO createReservationRequestDTO);

	@ApiOperation("Finds a reservation by its id.")
	@ApiResponses({@ApiResponse(code = 404, message = "Reservation not found.")})
    public ResponseEntity<ReservationDTO> findReservation(@ApiParam(value = "Reservation id.", example = "1") Long reservationId);

	@ApiOperation("Cancels a reservation by its id.")
	@ApiResponses({@ApiResponse(code = 404, message = "Reservation not found.")})
    public ResponseEntity<ReservationDTO> cancelReservation(@ApiParam(value = "Reservation id.", example = "1") Long reservationId);

	@ApiOperation("Reschedules a reservation.")
	@ApiResponses({@ApiResponse(code = 404, message = "Reservation not found.")})
    public ResponseEntity<ReservationDTO> rescheduleReservation(@ApiParam(value = "Reservation id.", example = "1") Long reservationId, 
    															@ApiParam(value = "Schedule id.", example = "1") Long scheduleId);
}
