package com.tenniscourts.guests;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class GuestController extends BaseRestController {

	private GuestService guestService;

	@PostMapping("/addReservation")
	@ApiOperation(value = "Add reservation")
	public ResponseEntity<Void> addReservation(@Valid @RequestBody GuestReservationDTO guestReservationDTO) {
		guestService.bookReservation(guestReservationDTO);
		return ResponseEntity.ok().build();
	}
}
