package com.tenniscourts.reservations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "ReservationController",description = "REST API related to Reservation Module")
@RestController("/api/book")
public class ReservationController extends BaseRestController {

	@Autowired
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
			this.reservationService = reservationService;
	}
    
    @ApiOperation(value = "Book Reservation")
    @ApiResponses(value = {
    		@ApiResponse(code = 200,message = "Success|OK"),
    		@ApiResponse(code = 401,message = "not authorized"),
    		@ApiResponse(code = 403,message = "forbidden"),
    		@ApiResponse(code = 404,message = "not found")
    })
    @PostMapping(path="/reservations",consumes="application/json",produces="application/json")
	public ResponseEntity<Void> bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }
    
    @ApiOperation(value = "Find Reservation by Id")
    @ApiResponses(value = {
    		@ApiResponse(code = 200,message = "Success|OK"),
    		@ApiResponse(code = 401,message = "not authorized"),
    		@ApiResponse(code = 403,message = "forbidden"),
    		@ApiResponse(code = 404,message = "not found")
    })
    @GetMapping("/reservations/{reservationId}")
    public ResponseEntity<ReservationDTO> findReservation(Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }
    
    @ApiOperation(value = "Cancel Reservation by Id")
    @ApiResponses(value = {
    		@ApiResponse(code = 200,message = "Success|OK"),
    		@ApiResponse(code = 401,message = "not authorized"),
    		@ApiResponse(code = 403,message = "forbidden"),
    		@ApiResponse(code = 404,message = "not found")
    })
    @PutMapping("/reservations/{reservationId}")
    public ResponseEntity<ReservationDTO> cancelReservation(Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }
    
    @ApiOperation(value = "Reschedule reservation by Id")
    @ApiResponses(value = {
    		@ApiResponse(code = 200,message = "Success|OK"),
    		@ApiResponse(code = 401,message = "not authorized"),
    		@ApiResponse(code = 403,message = "forbidden"),
    		@ApiResponse(code = 404,message = "not found")
    })
    @PutMapping("/reservations/{reservationId}/{scheduleId}")
    public ResponseEntity<ReservationDTO> rescheduleReservation(Long reservationId, Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
