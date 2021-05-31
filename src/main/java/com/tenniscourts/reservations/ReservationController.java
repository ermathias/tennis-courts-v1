package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Api(value = "Reservation Controller")
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @GetMapping("/reservations")
    @ApiOperation(value = "Find all reservations", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad Request!")})
    public ResponseEntity<List<ReservationDTO>> findAll() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @PostMapping("/reservation")
    @ApiOperation(value = "Book a reservation")
    @ApiResponse(code = 201, message = "Created")
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @GetMapping("/reservation/{reservationId}")
    @ApiOperation(value = "Find a reservation", response = ReservationDTO.class)
    @ApiResponse(code = 200, message = "Ok")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @DeleteMapping("/reservation/cancel/{reservationId}")
    @ApiOperation(value = "Cancel a reservation", response = ReservationDTO.class)
    @ApiResponse(code = 200, message = "Ok")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @PostMapping("/reservation/{reservationId}/schedule/{scheduleId}")
    @ApiOperation(value = "Reschedule a reservation", response = ReservationDTO.class)
    @ApiResponse(code = 200, message = "Ok")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable Long reservationId, @PathVariable Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
