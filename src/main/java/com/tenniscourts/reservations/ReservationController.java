package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/reservations")
@Api(value = "Reservations API")
public class ReservationController extends BaseRestController {

    @Autowired
    private final ReservationService reservationService;

    @Autowired
    private final ReservationMapper reservationMapper;

    @PostMapping
    @ApiOperation(value = "Creates a new reservation entity.")
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @GetMapping
    @ApiOperation(value = "Returns a list containing all reservations.")
    public ResponseEntity<List<ReservationDTO>> listAllReservations() {
        return ResponseEntity.ok().body(reservationMapper.map(reservationService.findAllReservations()));
    }

    @GetMapping("/history")
    @ApiOperation(value = "Returns a list of all past reservations.")
    public ResponseEntity<List<ReservationDTO>> listAllPastReservations() {
        return ResponseEntity.ok().body(reservationMapper.map(reservationService.findAllPastReservations()));
    }

    @GetMapping("/{reservationId}")
    @ApiOperation(value = "Returns only one reservation entity identified by its unique id.")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @GetMapping("/cancel/{reservationId}")
    @ApiOperation(value = "Cancels a specific reservation.")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @GetMapping("/reschedule/{reservationId}/{scheduleId}")
    @ApiOperation(value = "Reschedules a specific reservation.")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable Long reservationId, @PathVariable Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }

    @PostMapping("/finish")
    @ApiOperation(value = "Completes a specific reservation.")
    public ResponseEntity<ReservationDTO> finishReservation(@RequestBody FinishReservationDTO finishReservationDTO) {
        return ResponseEntity.ok().body(reservationMapper.map(reservationService.finishReservation(finishReservationDTO)));
    }
}
