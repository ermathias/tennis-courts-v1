package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "ReservationController", description = "Services for create, cancel, reschedule and find reservations")
@AllArgsConstructor
@RestController
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;



    @ApiOperation(value = "Book Reservation")
    @PostMapping("/reservation")
    public ResponseEntity<Void> bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @ApiOperation(value = "Find Reservation")
    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long  reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @ApiOperation(value = "Cancel Reservation")
    @DeleteMapping("/reservation/{reservationId}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @ApiOperation(value = "Find All Reservation")
    @GetMapping("/reservation")
    public ResponseEntity<List<ReservationDTO>> findAll() {
        return ResponseEntity.ok(reservationService.findAllReservation());
    }

    @ApiOperation(value = "Reschedule Reservation")
    @PutMapping("/reservation")
    public ResponseEntity<ReservationDTO> rescheduleReservation(Long reservationId, Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
