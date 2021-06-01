package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@Api(value = "ReservationController" , tags = {"Reservation Controller"})
@RestController
@RequestMapping(path = "api/reservation")
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @ApiOperation(value = "Book a new reservation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Reservation has been created")})
    @PostMapping("/book")
    public ResponseEntity<List<ReservationDTO>> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return new ResponseEntity<>(reservationService.bookReservation(createReservationRequestDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get a reservation by id")
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable("id") Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @ApiOperation(value = "Cancel a reservation by id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Reservation has been canceled")})
    @PostMapping("/cancel/{id}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable("id") Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @ApiOperation(value = "Reschedule a reservation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reservation has been rescheduled")})
    @PostMapping("/reschedule")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@RequestParam Long reservationId, @RequestParam Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
