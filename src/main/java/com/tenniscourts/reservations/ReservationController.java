package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationController extends BaseRestController {

    @Autowired
    private final ReservationService reservationService;

    @GetMapping("/all")
    @ApiOperation("Find all Reservations")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<List<ReservationDTO>> findAll() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @PostMapping
    @ApiOperation("Create and update a new Tennis Court Reservation")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Tennis Court successfully booked")})
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO dto) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(dto).getId())).build();
    }

    @GetMapping("/{reservationId}")
    @ApiOperation("Find a Reservation by ID")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<ReservationDTO> findReservationById(@PathVariable("reservationId") Long id) {
        return ResponseEntity.ok(reservationService.findReservationById(id));
    }

    @DeleteMapping("/{reservationId}")
    @ApiOperation("Cancel a Reservation by ID")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Reservation successfully cancelled")})
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable("reservationId") Long id) {
        return ResponseEntity.ok(reservationService.cancelReservation(id));
    }

    @PatchMapping("/{reservationId}/reschedule/{scheduleId}")
    @ApiOperation("Reschedule a Reservation by ID and Schedule ID")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Reservation successfully rescheduled")})
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable("reservationId") Long reservationId,
                                                                @PathVariable("scheduleId") Long scheduleId)
            throws Exception {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }

    @DeleteMapping("/missed/{reservationId}")
    @ApiOperation("Cancel a Reservation if a Guest missed it")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Reservation marked as Missed")})
    public ResponseEntity<ReservationDTO> missedReservation(@PathVariable("reservationId") Long id) {
        return ResponseEntity.ok(reservationService.missedReservation(id));
    }

    @PatchMapping("/finish/{reservationId}")
    @ApiOperation("Finish a Reservation and refund it")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Reservation marked as Finished")})
    public ResponseEntity<ReservationDTO> finishReservation(@PathVariable("reservationId") Long reservationId) {
        return ResponseEntity.ok(reservationService.finishReservation(reservationId));
    }

}
