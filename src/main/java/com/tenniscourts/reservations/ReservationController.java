package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/reservation")
public class ReservationController extends BaseRestController {

    @Autowired
    private final ReservationService reservationService;

    @ApiOperation(value = "Book a new Reservation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Booked a Reservation")})
    @PostMapping
    public ResponseEntity<Void> bookReservation(@Valid @RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @ApiOperation(value = "Find a Reservation by ID")
    @GetMapping(value = "/{reservationId}")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @ApiOperation(value = "Cancel a Reservation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Reservation Cancelled")})
    @PostMapping(value = "/{reservationId}/cancel")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @ApiOperation(value = "Confirm a Game played")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Reservation Used")})
    @PostMapping(value = "/{reservationId}/confirmgame")
    public ResponseEntity<ReservationDTO> confirmGameCompletion(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.confirmGameCompletion(reservationId));
    }

    @ApiOperation(value = "Reschedule a Reservation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reservation Rescheduled")})
    @PutMapping(value = "/reschedule")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@RequestParam Long reservationId, @RequestParam Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
