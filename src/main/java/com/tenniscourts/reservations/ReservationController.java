package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/reservations")
public class ReservationController extends BaseRestController {

    @Autowired
    private final ReservationService reservationService;

    @PostMapping("/bookreservation")
    public ResponseEntity<Void> bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @GetMapping("find/{id}")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) throws EntityNotFoundException {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @GetMapping("cancelreservation/{id}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) throws EntityNotFoundException{
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @PutMapping
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable Long reservationId,@PathVariable  Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
