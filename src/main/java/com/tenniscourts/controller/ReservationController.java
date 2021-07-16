package com.tenniscourts.controller;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.dto.CreateReservationRequestDTO;
import com.tenniscourts.dto.ReservationDTO;
import com.tenniscourts.service.ReservationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reservations")
@AllArgsConstructor
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @ApiOperation(value = "Add book reservation to a tennis court", notes = "This method add a book reservation to tennis court")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Schedule added!"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("/book")
    public ResponseEntity<ReservationDTO> bookReservation(
            @ApiParam(
                    name = "Reservation entity",
                    type = "CreateReservationRequestDTO",
                    value = "Reservation model for a tennis court",
                    required = true)
            @RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @ApiOperation(value = "Add list of book reservation to a tennis court", notes = "This method add a list of book reservation to tennis court")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Schedule added!"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("/book-list")
    public ResponseEntity<List<ReservationDTO>> bookReservationList(
            @ApiParam(
                    name = "List of Reservation entity",
                    type = "List<CreateReservationRequestDTO>",
                    value = "List of model of reservation for a tennis court",
                    required = true)
            @RequestBody List<CreateReservationRequestDTO> createReservationRequestDTOList) {

        return ResponseEntity.ok(reservationService.bookReservations(createReservationRequestDTOList));
    }

    @ApiOperation(value = "Get reservation by reservation Id", notes = "This method retrieve a reservation")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Reservation found!"),
    })
    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationDTO> findReservation(
            @ApiParam(
                    name = "reservationId",
                    type = "Long",
                    value = "Reservation Id",
                    example = "1",
                    required = true)
            @PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> findAll() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @PutMapping("/cancel/{reservationId}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @PutMapping("/reschedule/{reservationId}/{scheduleId}")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable Long reservationId, @PathVariable Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
