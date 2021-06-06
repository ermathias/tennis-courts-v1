package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;

import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.schedules.ScheduleRepository;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/reservation")
public class ReservationController extends BaseRestController {

    @Autowired
    private final ReservationService reservationService;
    @Autowired
    private final ReservationRepository reservationRepository;
    @Autowired
    private final ReservationMapper reservationMapper;
    @Autowired
    private final ScheduleRepository scheduleRepository;
    @Autowired
    private final GuestRepository guestRepository;


    @ApiOperation(value = "Book Reservation  Tennis Court", notes = "Reservation  Tennis Court")
    @PostMapping(path = "/bookReservation")
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }


    @ApiOperation(value = "Find Reservation  Tennis Court by reservationId", notes = "Find Reservation  Tennis Court")
    @GetMapping(path = "findReservation/{reservationId}")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable("reservationId") Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @ApiOperation(value = "Cancel Reservation  Tennis Court by reservationId", notes = "Cancel Reservation  Tennis Court")
    @GetMapping(path = "cancelReservation/{reservationId}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable("reservationId")Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @ApiOperation(value = "Reschedule  Tennis Court by reservationId and scheduleId", notes = "Specify the reservationId and scheduleId")
    @GetMapping(path = "rescheduleReservation/c/{reservationId}/scheduleId/{scheduleId}")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable("reservationId")Long reservationId,@PathVariable("scheduleId") Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }

}
