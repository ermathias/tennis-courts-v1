package com.tenniscourts.controller;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.storage.CreateReservationRequestDTO;
import com.tenniscourts.storage.ReservationDTO;
import com.tenniscourts.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(value = "ReservationController", tags = {"Reservation"})
@RestController
@RequestMapping("/v1/reservation")
@AllArgsConstructor
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @ApiOperation(httpMethod = "POST", value = "Get value",notes = "description")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request completed successfully"),
            @ApiResponse(code = 400, message = "There was a validation issue with the supplied request body"),
            @ApiResponse(code = 404, message = "No session was provided or could be found for the reservation"),
            @ApiResponse(code = 500, message = "Internal Error")})
    @PostMapping
    public ResponseEntity<Void> bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @ApiOperation(httpMethod = "GET", value = "Get value",notes = "description")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request completed successfully"),
            @ApiResponse(code = 400, message = "There was a validation issue with the supplied request body"),
            @ApiResponse(code = 404, message = "No session was provided or could be found for the reservation"),
            @ApiResponse(code = 500, message = "Internal Error")})
    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationDTO> findReservation(@RequestParam("reservationId") Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @ApiOperation(value = "Cancel the reservation of the given reservation id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request completed successfully"),
            @ApiResponse(code = 400, message = "There was a validation issue with the supplied request body"),
            @ApiResponse(code = 404, message = "No session was provided or could be found for the reservation"),
            @ApiResponse(code = 500, message = "Internal Error")})
    @PostMapping("/cancel/{reservationId}")
    public ResponseEntity<ReservationDTO> cancelReservation(@RequestParam("reservationId") Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @ApiOperation(value = "Reschedules a reservation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request completed successfully"),
            @ApiResponse(code = 400, message = "There was a validation issue with the supplied request body"),
            @ApiResponse(code = 404, message = "No session was provided or could be found for the reservation"),
            @ApiResponse(code = 500, message = "Internal Error")})
    @PostMapping("/reschedule/{reservationId}/schedule/{scheduleId}")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@RequestParam("reservationId") Long reservationId,
                                                                @RequestParam("scheduleId") Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
