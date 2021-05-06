package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservation")
@AllArgsConstructor
public class ReservationController extends BaseRestController {

	@Autowired
    private final ReservationService reservationService;

  
    @ApiOperation(Value='book_reservation', tags = "bookReservation")
	@PostMapping(path ="/bookReservation")
    public ResponseEntity<Void> bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @ApiOperation(Value='findreservation',response = TennisCourtDTO.class, tags = "findReservation")
    @GetMapping("/findReservation/{reservationId}")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable("reservationId") Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @ApiOperation(Value='cancel_reservation',description ="cancel Reservation")
    @deleteMapping("/cancelReservation/{reservationId}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable("reservationId") Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @ApiOperation(Value='reschedule_reservation',description ="reschedule Reservation")
    @PutMapping("/rescheduleReservation")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@Valid @RequestParam Long reservationId, Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
