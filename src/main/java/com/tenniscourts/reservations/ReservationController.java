package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/reservations")
public class ReservationController extends BaseRestController {

    @Autowired
    private final ReservationService reservationService;

    @PostMapping
    @ApiOperation("Create and update a new tennis court reservation")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Tennis Court successfully booked") })
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity
                .created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId()))
                .build();
    }

    @GetMapping("/all")
    @ApiOperation("Listing all reservations")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok") })
    public ResponseEntity<List<ReservationDTO>> findAll() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @PatchMapping("/{reservationId}/reschedule/{newScheduleId}")
    @ApiOperation("Reschedule reservation by reservation id and schedule id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully rescheduled") })
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable("reservationId") Long reservationId,
            @PathVariable("newScheduleId") Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }

    @GetMapping("/{reservationId}")
    @ApiOperation("Find reservation by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok") })
    public ResponseEntity<ReservationDTO> findReservationById(@PathVariable("reservationId") Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservationById(reservationId));
    }

    @DeleteMapping("/{reservationId}")
    @ApiOperation("Cancel reservation by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Reservation deleted with sucess") })
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable("reservationId") Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @DeleteMapping("/missed/{reservationId}")
    @ApiOperation("Cancel reservation if user missed")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Reservation marked as missing") })
    public ResponseEntity<ReservationDTO> missedReservation(@PathVariable("reservationId") Long reservationId) {
        return ResponseEntity.ok(reservationService.missedReservation(reservationId));
    }

    @PatchMapping("/concluded/{reservationId}")
    @ApiOperation("Complete the reservation and return the money")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Reservation marked as concluded") })
    public ResponseEntity<ReservationDTO> concludedReservation(@PathVariable("reservationId") Long reservationId) {
        return ResponseEntity.ok(reservationService.concludedReservation(reservationId));
    }
}
