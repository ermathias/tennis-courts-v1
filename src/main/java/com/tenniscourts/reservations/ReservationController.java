package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/reservations")
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @GetMapping
    public ResponseEntity<ReservationDTO> findReservation(Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }


    @PutMapping(value = "/{reservationId}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @PutMapping
    public ResponseEntity<ReservationDTO> rescheduleReservation(@RequestBody @Valid UpdateReservationRequestDTO updateReservationRequestDTO) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(updateReservationRequestDTO.getReservationId(), updateReservationRequestDTO.getScheduleId()));
    }
}
