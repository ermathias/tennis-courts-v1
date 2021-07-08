package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("Reservation")
@AllArgsConstructor
@RestController
@RequestMapping("/reservation")
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @PostMapping(value = "/book")
    @ApiOperation(value="Book a Reservation")
    @ApiResponse(code = 201, message = "Reservation created")
    public ResponseEntity<Void> bookReservation(@Valid @RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @GetMapping(value = "/find", params = "reservationId")
    @ApiOperation(value="Find a Reservation by ID")
    @ApiResponse(code = 200, message = "Reservation found")
    public ResponseEntity<ReservationDTO> findReservation(Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @PutMapping(value = "/cancel", params = "reservationId")
    @ApiOperation(value="Cancel a Reservation")
    @ApiResponse(code = 200, message = "Reservation cancelled")
    public ResponseEntity<ReservationDTO> cancelReservation(Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @PutMapping(value = "/reschedule", params = {"reservationId","scheduleId"})
    @ApiOperation(value="Reschedule a Reservation")
    @ApiResponse(code = 200, message = "Reservation cancelled")
    public ResponseEntity<ReservationDTO> rescheduleReservation(Long reservationId, Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
