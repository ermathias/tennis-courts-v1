package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.schedules.reschedule.RescheduleRequestDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/reservations")
@CrossOrigin(origins = "*")
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/book")
    @ApiOperation(value = "API operation that create a new Rservation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Reservation successfully created.")
    })
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(
                reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @GetMapping
    @ApiOperation(value = "API operation that return all reservations.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of reservations found."),
            @ApiResponse(code = 404, message = "List of reservations not found.")
    })
    public ResponseEntity<List<ReservationDTO>> findAllReservations() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "API operation that return a reservation by ID number.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reservation found."),
            @ApiResponse(code = 404, message = "Reservation not found.")
    })
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.findReservation(id));
    }

    @PutMapping("/reschedule/{reservationId}/{scheduleId}")
    @ApiOperation(value = "API operation that reschedule a guest by ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reservation successfully rescheduled."),
            @ApiResponse(code = 404, message = "Reservation not found.")
    })
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable Long reservationId, @PathVariable Long scheduleId, @RequestBody RescheduleRequestDTO rescheduleRequestDTO) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId, rescheduleRequestDTO));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "API operation that delete a reservation by ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reservation successfully deleted."),
            @ApiResponse(code = 404, message = "Reservation not found.")
    })
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.cancelReservation(id));
    }


}