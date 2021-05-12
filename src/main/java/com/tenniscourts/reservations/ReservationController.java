package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/reservations")
@AllArgsConstructor
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @ApiOperation(value = "Makes reservations given a list of schedules and guests")
    @PostMapping("/bulk")
    public ResponseEntity<Void> bookReservations(@RequestBody List<CreateReservationRequestDTO> createReservationRequestDTO) {
        reservationService.bulkBookReservations(createReservationRequestDTO);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Makes a reservation given a guest id and a schedule id")
    @PostMapping
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @ApiOperation(value = "Returns a reservation given a reservation id")
    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @ApiOperation(value = "Remove the reservation of the given reservation id")
    @PostMapping("/{reservationId}/cancel")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @ApiOperation(value = "Reschedules a reservation")
    @PostMapping("/{reservationId}/reschedule/{newScheduleId}")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable Long reservationId, @PathVariable Long newScheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, newScheduleId));
    }
    
    @ApiOperation(value = "Mark reservation as completed")
    @PostMapping("/{reservationId}/completed")
    public ResponseEntity<ReservationDTO> markReservationAsCompleted(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.markAsCompleted(reservationId));
    }
}
