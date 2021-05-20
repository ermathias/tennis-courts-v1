package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Api(value = "ReservationController", description = "Operations pertaining to reservation of tennis courts")
@RestController
@RequestMapping("/reservation")
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @ApiOperation(value = "Book reservation", response = ResponseEntity.class, tags = "Book reservation")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @ApiOperation(value = "Find reservation by Id", response = ResponseEntity.class, tags = "Find reservation by Id")
    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable(value = "id") Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @ApiOperation(value = "Cancel reservation by Id", response = ResponseEntity.class, tags = "Cancel reservation by Id")
    @RequestMapping(value = "/cancel/{id}", method = RequestMethod.GET)
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable(value = "id") Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @ApiOperation(value = "Reschedule reservation", response = ResponseEntity.class, tags = "Reschedule reservation")
    @RequestMapping(value = "/reschedule", method = RequestMethod.POST)
    public ResponseEntity<ReservationDTO> rescheduleReservation(@RequestBody RescheduleReservation rescheduleReservation) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(rescheduleReservation.getReservationId(),
                rescheduleReservation.getScheduleId()));
    }

    /*Problem 1.As a User I want to be able to book a reservation for one or
    more tennis court at a given date schedule*/
    @ApiOperation(value = "Book reservation by Date", response = ResponseEntity.class, tags = "Book reservation by Date")
    @RequestMapping(value = "/byDate", method = RequestMethod.POST)
    public ResponseEntity<Void> bookReservationByDate(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }
}
