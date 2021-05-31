package com.tenniscourts.reservations;

import static com.tenniscourts.utils.TennisCourtsConstraints.*;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.utils.TennisCourtsConstraints;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping(path = TennisCourtsConstraints.RESERVATION_API_PATH)
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @ApiResponse(code = 201, message = "Reservation created")
    @PostMapping(CREATE_PATH)
    public ResponseEntity<Void> bookReservation(@Valid @RequestBody CreateReservationRequestDTO createReservationRequestDTO, 
    		BindingResult bindingResult) {
    	
    	validateInputs(bindingResult);
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @ApiResponses({
    	@ApiResponse(code = 200, message = "Reservation found"),
   	 	@ApiResponse(code = 404, message = "Reservation not found")
    })
    @GetMapping(FINDBYID_PATH + "/{reservationId}")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @PostMapping(CANCEL_PATH + "/{reservationId}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @ApiResponse(code = 200, message = "Rescheduled")
    @PostMapping(RESCHEDULE_PATH)
    public ResponseEntity<ReservationDTO> rescheduleReservation(@Valid @RequestBody RescheduleReservationRequestoDTO dto,
    		BindingResult bindingResult) {
    	
    	validateInputs(bindingResult);
        return ResponseEntity.ok(reservationService.rescheduleReservation(dto.getReservationId(), dto.getScheduleId()));
    }
}
