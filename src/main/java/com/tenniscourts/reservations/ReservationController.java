package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.exceptions.AdminConstraint;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reservation")
@AllArgsConstructor
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @ApiOperation(value = "Book a reservation.")
    @PostMapping(value = "bookReservation")
    public ResponseEntity<Void> bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @ApiOperation(value = "Find a reservation.")
    @GetMapping(value = "findReservation")
    public ResponseEntity<ReservationDTO> findReservation(Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @ApiOperation(value = "Cancel a reservation.")
    @PutMapping(value = "cancelReservation")
    public ResponseEntity<ReservationDTO> cancelReservation(Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @ApiOperation(value = "Reschedule a reservation.")
    @PutMapping(value = "rescheduleReservation")
    public ResponseEntity<ReservationDTO> rescheduleReservation(Long reservationId, Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }

    @ApiOperation(value = "Show all past reservations. Only admin can call this method.")
    @GetMapping(value = "/myReservations")
    public ResponseEntity<List<ReservationDTO>> getMyReservations(@RequestHeader(defaultValue = "true", required = false)
                                                                      @AdminConstraint String isAdmin, Long guestId) {
        return ResponseEntity.ok(reservationService.getAllMyReservations(guestId));
    }
}
