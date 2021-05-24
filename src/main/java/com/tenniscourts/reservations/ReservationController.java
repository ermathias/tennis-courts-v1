package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.exceptions.EntityNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@Api(value = "API for reservations")
@RequestMapping("api/reservations")
public class ReservationController extends BaseRestController {

    @Autowired
    private final ReservationService reservationService;

    @ApiOperation("Adds a new reservation")
    @PostMapping("add")
    public ResponseEntity<Void> bookReservation(@Valid @RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @ApiOperation("Search for a reservation by reservation id")
    @GetMapping(path = "find/{id}")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable("id") Long reservationId) throws EntityNotFoundException {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @ApiOperation("Get all reservations")
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> findAllReservations(){
        return ResponseEntity.ok(reservationService.findAllReservations());
    }

    @ApiOperation("Cancel a reservation")
    @GetMapping("cancelreservation/{id}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) throws EntityNotFoundException{
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @ApiOperation("Reschedule a reservation")
    @PutMapping("{reservationId}/{scheduleId}")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable("reservationId") Long reservationId,@PathVariable("scheduleId")  Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
