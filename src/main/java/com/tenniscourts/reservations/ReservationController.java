package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @PostMapping("/doReservation")
    @ApiOperation("do a reservation")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "do a reservation") })
    public ResponseEntity<ReservationDTO> bookReservation(@Valid @RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @GetMapping("/findReservation{reservationId}")
    @ApiOperation("Find a reservation")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Find a reservation") })
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @PostMapping("/{reservationId}/{newScheduledId}")
    @ApiOperation("Cancel Or Reschedule Reservation")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Canceled Or Rescheduled Reservation") })
    public ResponseEntity<Object> cancelOrRescheduleReservation(@PathVariable("reservationId") Long reservationId, @PathVariable(required = false, name = "newScheduledId") Long newScheduledId) {
        try{
        if(newScheduledId != null) {
            return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, newScheduledId));
        }else{
            return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
        }
        }catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }

     @PostMapping("/rescheduleReservation/{reservationId}/{newScheduledId}")
    @ApiOperation("Reschedule a reservation")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Reschedule a reservation") })
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable Long reservationId, @PathVariable Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
