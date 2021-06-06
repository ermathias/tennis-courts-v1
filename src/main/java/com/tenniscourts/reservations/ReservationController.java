package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@AllArgsConstructor
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @PostMapping(value = "/")
    @ApiOperation("Book a reservation")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created reservation")})
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @GetMapping(value = "/{reservationId}")
    @ApiOperation("Find a reservation")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Find a reservation - success")})
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @DeleteMapping(value = "/{reservationId}")
    @ApiOperation("Cancel a reservation")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Cancel a reservation - success")})
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @PutMapping(value = "/{reservationId}")
    @ApiOperation("Reschedule a reservation")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Reschedule a reservation - success")})
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable Long reservationId, @RequestBody Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }

    //    @GetMapping(value = "/")
    @ApiOperation("Find all reservations")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Find all reservations - success")})
    public ResponseEntity<List<ReservationDTO>> findAllReservations() {
        return ResponseEntity.ok(reservationService.findAllReservations());
    }
}
