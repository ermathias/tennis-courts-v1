package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @RequestMapping(value = "/bookReservation", method = RequestMethod.POST)
    public ResponseEntity<ReservationDTO> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @RequestMapping(value = "/findReservation/{reservationId}", method = RequestMethod.GET)
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @RequestMapping(value = "/findReservation", method = RequestMethod.GET)
    public ResponseEntity<List<ReservationDTO>> findAllReservation() {
        return ResponseEntity.ok(reservationService.findAllReservation());
    }

    @RequestMapping(value = "/cancelReservation/{reservationId}", method = RequestMethod.GET)
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @RequestMapping(value = "/rescheduleReservation/{reservationId}/{scheduleId}", method = RequestMethod.GET)
    @GetMapping
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable Long reservationId, @PathVariable Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
