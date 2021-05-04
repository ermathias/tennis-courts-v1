package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
public class ReservationController extends BaseRestController {

	@Autowired
    private final ReservationService reservationService;

    @PostMapping(value = "/book", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @GetMapping(value= "/{reservationid}", consumes="*/*", produces = "application/json")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable("reservationid") Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @PutMapping(value= "/{reservationid}", consumes="*/*", produces = "application/json")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable("reservationid") Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }
    @PostMapping(value= "/reschedule", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@RequestBody RescheduleReservationRequestDTO rescheduleReservationRequestDTO) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(rescheduleReservationRequestDTO));
    }
}
