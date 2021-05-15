package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.config.swagger.SwaggerConfig;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Reservation Controller
 */
@AllArgsConstructor
@RestController
@RequestMapping("/reservations")
@Api(value = SwaggerConfig.RESERVATION_VALUE, tags = SwaggerConfig.RESERVATION_ENDPOINT)
public class ReservationController extends BaseRestController {

	private final ReservationService reservationService;

	/**
	 * To Book reservation
	 * 
	 * @param createReservationRequestDTO,
	 *            request info
	 */

	@ApiOperation(value = "Book reservation", tags = SwaggerConfig.RESERVATION_ENDPOINT)
	@ApiResponses({ @ApiResponse(code = SwaggerConfig.CREATED_CODE, message = SwaggerConfig.CREATED_MESSAGE),
			@ApiResponse(code = SwaggerConfig.BAD_REQUEST_CODE, message = SwaggerConfig.BAD_REQUEST_MESSAGE),
			@ApiResponse(code = SwaggerConfig.INTERNAL_SERVER_ERROR_CODE, message = SwaggerConfig.INTERNAL_SERVER_ERROR_MESSAGE)

	})
	@PostMapping
	public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
		return ResponseEntity
				.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId()))
				.build();
	}

	/**
	 * To retrieve reservation by Id
	 * 
	 * @param reservationId
	 * @return {@link ReservationDTO}
	 */

	@ApiOperation(value = "Retrieve reservation court by Id", tags = SwaggerConfig.RESERVATION_ENDPOINT)
	@ApiResponses({ @ApiResponse(code = SwaggerConfig.OK_CODE, message = SwaggerConfig.OK_MESSAGE),
			@ApiResponse(code = SwaggerConfig.BAD_REQUEST_CODE, message = SwaggerConfig.BAD_REQUEST_MESSAGE),
			@ApiResponse(code = SwaggerConfig.NOT_FOUND_CODE, message = SwaggerConfig.NOT_FOUND_MESSAGE),
			@ApiResponse(code = SwaggerConfig.INTERNAL_SERVER_ERROR_CODE, message = SwaggerConfig.INTERNAL_SERVER_ERROR_MESSAGE) })
	@GetMapping("/{id}")
	public ResponseEntity<ReservationDTO> findReservation(@PathVariable("id")Long reservationId) {
		return ResponseEntity.ok(reservationService.findReservation(reservationId));
	}

	/**
	 * To cancel reservation by Id
	 * 
	 * @param reservationId
	 * @return {@link ReservationDTO}
	 */

	@ApiOperation(value = "To cancel reservation by Id", tags = SwaggerConfig.RESERVATION_ENDPOINT)
	@ApiResponses({ @ApiResponse(code = SwaggerConfig.OK_CODE, message = SwaggerConfig.OK_MESSAGE),
			@ApiResponse(code = SwaggerConfig.BAD_REQUEST_CODE, message = SwaggerConfig.BAD_REQUEST_MESSAGE),
			@ApiResponse(code = SwaggerConfig.NOT_FOUND_CODE, message = SwaggerConfig.NOT_FOUND_MESSAGE),
			@ApiResponse(code = SwaggerConfig.INTERNAL_SERVER_ERROR_CODE, message = SwaggerConfig.INTERNAL_SERVER_ERROR_MESSAGE) })
	@PutMapping("/{id}")
	public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable("id")Long reservationId) {
		return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
	}

	/**
	 * To reschedule reservation
	 * 
	 * @param reservationId
	 * @param scheduleId
	 * @return {@link ReservationDTO}
	 */

	@ApiOperation(value = "To reschedule reservation", tags = SwaggerConfig.RESERVATION_ENDPOINT)
	@ApiResponses({ @ApiResponse(code = SwaggerConfig.OK_CODE, message = SwaggerConfig.OK_MESSAGE),
			@ApiResponse(code = SwaggerConfig.BAD_REQUEST_CODE, message = SwaggerConfig.BAD_REQUEST_MESSAGE),
			@ApiResponse(code = SwaggerConfig.NOT_FOUND_CODE, message = SwaggerConfig.NOT_FOUND_MESSAGE),
			@ApiResponse(code = SwaggerConfig.INTERNAL_SERVER_ERROR_CODE, message = SwaggerConfig.INTERNAL_SERVER_ERROR_MESSAGE) })
	@GetMapping("/{id}/schedules/{scheduleID}")
	public ResponseEntity<ReservationDTO> rescheduleReservation(Long reservationId, Long scheduleId) {
		return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
	}
}
