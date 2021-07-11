package com.tenniscourts.controller;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.dto.ReservationDTO;
import com.tenniscourts.dto.CreateReservationRequestDTO;
import com.tenniscourts.service.ReservationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService){
        this.reservationService = reservationService;
    }


    @ApiOperation("Create user reservation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "A reservation has been created successfully"),
            @ApiResponse(code = 400, message = "Bad request. Check your input"),
            @ApiResponse(code = 500, message = "An error has occurred in creating the reservation")
    })
    @RequestMapping(value = "/api/reservation/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @ApiOperation("Get reservation by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A reservation has been successfully retrieved"),
            @ApiResponse(code = 400, message = "Bad request. Check your input"),
            @ApiResponse(code = 500, message = "An error has occurred in getting a reservation by ID")
    })
    @RequestMapping(value = "/api/reservation/get", method = RequestMethod.GET)
    public ResponseEntity<ReservationDTO> findReservation(@RequestParam Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @ApiOperation("Cancel reservation by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A reservation has been successfully deleted"),
            @ApiResponse(code = 400, message = "Bad request. Check your input"),
            @ApiResponse(code = 500, message = "An error has occurred in deleting a reservation")
    })
    @RequestMapping(value = "/api/reservation/delete", method = RequestMethod.DELETE)
    public ResponseEntity<ReservationDTO> cancelReservation(@RequestParam Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @ApiOperation("Reschedule reservation by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A reservation has been successfully rescheduled"),
            @ApiResponse(code = 400, message = "Bad request. Check your input"),
            @ApiResponse(code = 500, message = "An error has occurred in rescheduling a reservation")
    })
    @RequestMapping(value = "/api/reservation/modify", method = RequestMethod.PUT)
    public ResponseEntity<ReservationDTO> rescheduleReservation(@RequestParam Long reservationId, @RequestParam Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }

}
