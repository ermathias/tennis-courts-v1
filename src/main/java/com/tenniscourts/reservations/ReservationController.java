package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@RestController

@Controller
@RequestMapping("/v1/reservation")
@AllArgsConstructor
public class ReservationController extends BaseRestController {

    @Autowired
    private ReservationService reservationService;

    @ApiOperation(value = "Makes reservations given a list of schedules and guests")
    @PostMapping("/bulk")
    public ResponseEntity<Void> bookReservations(@RequestBody List<CreateReservationRequestDTO> createReservationRequestDTO) {
        reservationService.bulkBookReservations(createReservationRequestDTO);
        return ResponseEntity.ok().build();
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
