package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Api(value = "ReservationController")
@RequestMapping("/reservations")
@RestController
@AllArgsConstructor
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @ApiOperation(value = "Book a reservation", tags = "bookReservation")
    @PostMapping("/bookReservation")
    public ResponseEntity<Void> bookReservation(@Valid @RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @ApiOperation(value = "Find a reservation by id", tags = "findReservation")
    @GetMapping("/findReservation/{reservationId}")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @ApiOperation(value = "Cancel a reservation by id", tags = "cancelReservation")
    @DeleteMapping("/cancelReservation/{reservationId}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @ApiOperation(value = "Reschedule a reservation", tags = "rescheduleReservation")
    @PutMapping("/rescheduleReservation/{reservationId}/{scheduleId}")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable Long reservationId,@PathVariable Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }

    @ApiOperation(value = "See past reservations", tags = "findPastReservations")
    @GetMapping("/findPastReservations")
    public ResponseEntity<List<ReservationDTO>> findPastReservations() {
        return ResponseEntity.ok(reservationService.findPastReservations(LocalDateTime.now()));
    }
}
