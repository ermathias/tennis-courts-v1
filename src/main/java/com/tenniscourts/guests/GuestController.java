package com.tenniscourts.guests;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.config.swagger.SwaggerConfig;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Guest Controller
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/guests")
@Api(value = SwaggerConfig.GUEST_VALUE, tags = SwaggerConfig.GUEST_ENDPOINT)
@Slf4j
@Validated
public class GuestController extends BaseRestController {

	private final GuestService guestService;

	/**
	 * To insert guest
	 * 
	 * @param guestDTO,
	 *            request info
	 */

	@ApiOperation(value = "To insert guest", tags = SwaggerConfig.GUEST_ENDPOINT)
	@ApiResponses({ @ApiResponse(code = SwaggerConfig.CREATED_CODE, message = SwaggerConfig.CREATED_MESSAGE),
			@ApiResponse(code = SwaggerConfig.BAD_REQUEST_CODE, message = SwaggerConfig.BAD_REQUEST_MESSAGE),
			@ApiResponse(code = SwaggerConfig.INTERNAL_SERVER_ERROR_CODE, message = SwaggerConfig.INTERNAL_SERVER_ERROR_MESSAGE)

	})
	@PostMapping
	public ResponseEntity<Void> addGuest(@RequestBody @Valid GuestRequest guestRequest, BindingResult bindingResultS) {
		log.info("Adding guest");
		return ResponseEntity.created(locationByEntity(guestService.addGuest(guestRequest).getId())).build();
	}

	/**
	 * To retrieve guests by Id
	 * 
	 * @param guestId
	 */

	@ApiOperation(value = "To retrieve guests by Id", tags = SwaggerConfig.GUEST_ENDPOINT)
	@ApiResponses({ @ApiResponse(code = SwaggerConfig.OK_CODE, message = SwaggerConfig.OK_MESSAGE),
			@ApiResponse(code = SwaggerConfig.BAD_REQUEST_CODE, message = SwaggerConfig.BAD_REQUEST_MESSAGE),
			@ApiResponse(code = SwaggerConfig.NOT_FOUND_CODE, message = SwaggerConfig.NOT_FOUND_MESSAGE),
			@ApiResponse(code = SwaggerConfig.INTERNAL_SERVER_ERROR_CODE, message = SwaggerConfig.INTERNAL_SERVER_ERROR_MESSAGE) })
	@GetMapping("/{id}")
	public ResponseEntity<GuestDTO> findGuestById(@PathVariable("id") Long guestId) {
		return ResponseEntity.ok(guestService.findGuestById(guestId));
	}

	/**
	 * To retrieve guests by name
	 * 
	 * @param guestId
	 */

	@ApiOperation(value = "To retrieve guests by name", tags = SwaggerConfig.GUEST_ENDPOINT)
	@ApiResponses({ @ApiResponse(code = SwaggerConfig.OK_CODE, message = SwaggerConfig.OK_MESSAGE),
			@ApiResponse(code = SwaggerConfig.BAD_REQUEST_CODE, message = SwaggerConfig.BAD_REQUEST_MESSAGE),
			@ApiResponse(code = SwaggerConfig.INTERNAL_SERVER_ERROR_CODE, message = SwaggerConfig.INTERNAL_SERVER_ERROR_MESSAGE) })
	@GetMapping
	public ResponseEntity<List<GuestDTO>> findGuestByName(@RequestParam @NotBlank String name) {
		log.info("Finding guest");
		return ResponseEntity.ok(guestService.findGuestByName(name));
	}

	/**
	 * To update guest
	 * 
	 * @param guestDTO
	 */

	@ApiOperation(value = "To update guest", tags = SwaggerConfig.GUEST_ENDPOINT)
	@ApiResponses({ @ApiResponse(code = SwaggerConfig.OK_CODE, message = SwaggerConfig.OK_MESSAGE),
			@ApiResponse(code = SwaggerConfig.BAD_REQUEST_CODE, message = SwaggerConfig.BAD_REQUEST_MESSAGE),
			@ApiResponse(code = SwaggerConfig.NOT_FOUND_CODE, message = SwaggerConfig.NOT_FOUND_MESSAGE),
			@ApiResponse(code = SwaggerConfig.INTERNAL_SERVER_ERROR_CODE, message = SwaggerConfig.INTERNAL_SERVER_ERROR_MESSAGE) })
	@PutMapping
	public ResponseEntity<GuestDTO> modifyGuest(@RequestBody @Valid GuestDTO guestDTO, BindingResult bindingResult) {
		log.info("Started modifying guest");
		return ResponseEntity.ok(guestService.modifyGuest(guestDTO));
	}

	/**
	 * To find all guest
	 * 
	 * @param guestId
	 */

	@ApiOperation(value = "To find all guest", tags = SwaggerConfig.GUEST_ENDPOINT)
	@ApiResponses({ @ApiResponse(code = SwaggerConfig.OK_CODE, message = SwaggerConfig.OK_MESSAGE),
			@ApiResponse(code = SwaggerConfig.BAD_REQUEST_CODE, message = SwaggerConfig.BAD_REQUEST_MESSAGE),
			@ApiResponse(code = SwaggerConfig.INTERNAL_SERVER_ERROR_CODE, message = SwaggerConfig.INTERNAL_SERVER_ERROR_MESSAGE) })
	@GetMapping("/all")
	public ResponseEntity<List<GuestDTO>> findAllGuest() {
		log.info("Started finding guest");
		return ResponseEntity.ok(guestService.findAllGuests());
	}

	/**
	 * To delete guest by Id
	 * 
	 * @param guestId
	 */

	@ApiOperation(value = "To delete guest by Id", tags = SwaggerConfig.GUEST_ENDPOINT)
	@ApiResponses({ @ApiResponse(code = SwaggerConfig.OK_CODE, message = SwaggerConfig.OK_MESSAGE),
			@ApiResponse(code = SwaggerConfig.BAD_REQUEST_CODE, message = SwaggerConfig.BAD_REQUEST_MESSAGE),
			@ApiResponse(code = SwaggerConfig.INTERNAL_SERVER_ERROR_CODE, message = SwaggerConfig.INTERNAL_SERVER_ERROR_MESSAGE) })
	@DeleteMapping("/{id}")
	public ResponseEntity<String> removeGuest(@PathVariable("id") Long guestId) {
		log.info("Started removing guest");
		return ResponseEntity.ok(guestService.removeGuest(guestId));
	}
}
