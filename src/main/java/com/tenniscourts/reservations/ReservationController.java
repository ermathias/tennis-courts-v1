package com.tenniscourts.reservations;

import java.util.List;

import javax.inject.Inject;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@AllArgsConstructor
@RestController
@RequestMapping("/api/reservations")
@Api("Reservations API")
@CrossOrigin(origins = "*")
public class ReservationController extends BaseRestController {

    @Inject
    private final ReservationService reservationService;

    @ApiOperation(value = "Book a reservation to a tennis court.")
    @PostMapping(value = "/book")
    public ResponseEntity<Void> bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @ApiOperation(value = "Find a book reservation by its id.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable("id") Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @ApiOperation(value = "Cancel a book reservation by its id.")
    @PutMapping(value = "/cancel/{id}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable("id")  Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @ApiOperation(value = "Reschedule a book reservation of a tennis court.")
    @PostMapping(value = "/reschedule")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@RequestParam("reservationId") Long reservationId, @RequestParam("scheduleId") Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }

    @ApiOperation(value = "Find the time slots that are currently free.")
    @GetMapping(value = "/freeTimeSlots")
    public ResponseEntity<List<ReservationDTO>> findFreeTimeSlots() {
        return ResponseEntity.ok(reservationService.findFreeTimeSlots());
    }
}