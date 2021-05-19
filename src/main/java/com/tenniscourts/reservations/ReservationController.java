package com.tenniscourts.reservations;

import io.swagger.annotations.ApiOperation;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;

import javax.websocket.server.PathParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController("/reservation")
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @ApiOperation(value = "Book Reservation")
    @PostMapping("/book")
    @ResponseBody
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) throws Exception{
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    public ResponseEntity<ReservationDTO> findReservation(Long reservationId) throws Exception{
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    public ResponseEntity<ReservationDTO> cancelReservation(Long reservationId) throws Exception{
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @PutMapping("/reschedule/{reservationId}/{scheduleId}")
    @ResponseBody
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable ("reservationId") Long reservationId, @PathVariable ("scheduleId") Long scheduleId) throws Exception{
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
