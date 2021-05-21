package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/reservations")
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @PostMapping
    @ApiOperation("Books a new reservation for a guest into an available slot")
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @GetMapping
    @ApiOperation("Fetches all reservations")
    @ApiParam(name="past", value = "Returns only elapsed reservations")
    public ResponseEntity<List<ReservationDTO>> findReservations(@RequestParam(defaultValue = "false") boolean past) {
        return ResponseEntity.ok(reservationService.findReservations(past));
    }

    @GetMapping("/{reservationId}")
    @ApiOperation("Fetches a reservation by id")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @DeleteMapping("/{reservationId}")
    @ApiOperation("Cancel an active reservation by id. Calculates refund value based on advance time")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @PostMapping("/{reservationId}/reschedule")
    @ApiOperation("Reschedules an active reservation by id into the new available slot. Effectively cancels the previous booking, calculating refund value based on advance time")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable Long reservationId, @RequestBody ReservationRescheduleRequestDTO reservationRescheduleRequestDTO) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, reservationRescheduleRequestDTO.getScheduleId()));
    }
}
