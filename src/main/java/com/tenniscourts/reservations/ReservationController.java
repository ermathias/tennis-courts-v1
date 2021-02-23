package com.tenniscourts.reservations;

import java.util.List;

import javax.inject.Inject;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@AllArgsConstructor
@RestController
@RequestMapping("/api/reservations")
@Api(description = "Reservations REST API")
@CrossOrigin(origins = "*")
public class ReservationController extends BaseRestController {

    @Inject
    private final ReservationService reservationService;

    @PostMapping
    @ApiOperation(value = "Book a reservation to a tennis court.")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "The reservation has been added to the tennis court."),
        @ApiResponse(code = 404, message = "The informed parameters have been produced validation error(s).")
    })
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Find a reservation by its id.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "A reservation object.")
    })
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable("id") Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @PutMapping(value = "/cancel/{id}")
    @ApiOperation(value = "Cancel a reservation by its id.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The canceled reservation object."),
        @ApiResponse(code = 404, message = "The informed reservation was not found."),
        @ApiResponse(code = 400, message = "The informed reservation have been produced validation error(s).")
    })
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable("id")  Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @PostMapping(value = "/reschedule")
    @ApiOperation(value = "Reschedule a reservation of a tennis court.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The rescheduled reservation object."),
        @ApiResponse(code = 404, message = "The informed reservation was not found."),
        @ApiResponse(code = 400, message = "The informed reservation produced validation error(s).")
    })
    public ResponseEntity<ReservationDTO> rescheduleReservation(@RequestParam("reservationId") Long reservationId, @RequestParam("scheduleId") Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}