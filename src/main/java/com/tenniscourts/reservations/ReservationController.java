package com.tenniscourts.reservations;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @ApiOperation(value = "Create new Reservation")
    @ApiResponses(value = {
    	    @ApiResponse(code = 200, message = "Create new Reservation")})
    @PutMapping(value = "/reservation")
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @ApiOperation(value = "Find Reservation")
    @ApiResponses(value = {
    	    @ApiResponse(code = 200, message = "Find Reservation")})
    @GetMapping(value = "/reservation/{reservationId}", produces = "application/json")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @ApiResponses(value = {
    	    @ApiResponse(code = 200, message = "Cancel Reservation")})
    @GetMapping(value = "/reservation/cancel/{reservationId}", produces = "application/json")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @ApiOperation(value = "Reschedule Reservation")
    @ApiResponses(value = {
    	    @ApiResponse(code = 200, message = "Reschedule Reservation")})
    @GetMapping(value = "/reservation/reschedule/{reservationId}/schedule/{scheduleId}", produces = "application/json")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable Long reservationId, @PathVariable Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
    
    //TODO: implement rest and swagger
    @ApiOperation(value = "Returns a full reservations list")
    @ApiResponses(value = {
    	    @ApiResponse(code = 200, message = "Returns the reservations list")})
    @GetMapping(value = "/reservations", produces = "application/json")
    public ResponseEntity<List<Reservation>> findTennisCourts() {
        return ResponseEntity.ok(reservationService.findReservations());
    }
    
}
