package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/reservations")
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @PostMapping("/book")
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable("id") Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable("id") Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @PostMapping("/reschedule/{reservationId}/{scheduleId}")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable("reservationId") Long reservationId,
                                                                @PathVariable("scheduleId") Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
