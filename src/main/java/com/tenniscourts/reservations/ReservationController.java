package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/reservation")
public class ReservationController extends BaseRestController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/book")
    public ResponseEntity<Long> bookReservation(@RequestBody ReservationDTO reservationDTO) {
        reservationService.bookReservation(reservationDTO);
        return ResponseEntity.created(locationByEntity((reservationDTO).getId())).build();
    }

    @GetMapping("/book/{reservationId}")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) throws Throwable {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @GetMapping("/cancel/{reservationId}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        try {
            return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
        } catch (Throwable e) {
            return ResponseEntity.of(Optional.empty());
        }
    }

    public ResponseEntity<ReservationDTO> rescheduleReservation(Long reservationId, Long scheduleId) {
        try {
            return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
        } catch (Throwable e) {
            return ResponseEntity.of(Optional.empty());
        }
    }
}
