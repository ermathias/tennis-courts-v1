package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @RequestMapping(value = "/book-reservation", method = RequestMethod.POST)
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @RequestMapping(value = "/find-reservation", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ReservationDTO> findReservation(@RequestParam("reservationId") Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @RequestMapping(value = "/cancel-reservation", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ReservationDTO> cancelReservation(@RequestParam("reservationId") Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @RequestMapping(value = "/reschedule-reservation", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ReservationDTO> rescheduleReservation(
            @RequestParam("reservationId") Long reservationId,
            @RequestParam("scheduleId") Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
