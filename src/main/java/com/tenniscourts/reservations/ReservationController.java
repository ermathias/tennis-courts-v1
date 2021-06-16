package com.tenniscourts.reservations;

import com.tenniscourts.UriConstants;
import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(path = UriConstants.RESERVATION_PATH)
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @PostMapping(path = UriConstants.BOOK_PATH)
    @ApiOperation("Book a new reservation")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Reservation booked successfully") })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> bookReservation(@RequestBody @Valid CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @GetMapping(path = UriConstants.RESERVATION_ID_VARIABLE)
    @ApiOperation("Find an existing reservation by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservationById(reservationId));
    }

    @DeleteMapping(path = UriConstants.RESERVATION_ID_VARIABLE)
    @ApiOperation("Cancel an existing reservation by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Reservation cancelled successfully") })
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservationById(reservationId));
    }

    @PutMapping(path = UriConstants.RESCHEDULE_PATH + UriConstants.RESERVATION_ID_VARIABLE + UriConstants.SCHEDULE_ID_VARIABLE)
    @ApiOperation("Reschedule an existing reservation by id and scheduleId")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Reservation rescheduled successfully") })
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable Long reservationId, @PathVariable Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
