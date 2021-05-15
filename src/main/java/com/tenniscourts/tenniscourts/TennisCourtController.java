package com.tenniscourts.tenniscourts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.config.swagger.SwaggerConfig;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

/**
 * Tennis Court end points
 */

@AllArgsConstructor
@RestController
@RequestMapping("/tennis-courts")
@Api(value = SwaggerConfig.TENNIS_COURTS_VALUE, tags = SwaggerConfig.TENNIS_COURTS_ENDPOINT)
public class TennisCourtController extends BaseRestController {

	private final TennisCourtService tennisCourtService;

	/**
	 * To insert tennis Court
	 * 
	 * @param tennisCourtRequest,
	 *            request info
	 */

	@ApiOperation(value = "Insert Tennis Court", tags = SwaggerConfig.TENNIS_COURTS_ENDPOINT)
	@ApiResponses({ @ApiResponse(code = SwaggerConfig.CREATED_CODE, message = SwaggerConfig.CREATED_MESSAGE),
			@ApiResponse(code = SwaggerConfig.BAD_REQUEST_CODE, message = SwaggerConfig.BAD_REQUEST_MESSAGE),
			@ApiResponse(code = SwaggerConfig.INTERNAL_SERVER_ERROR_CODE, message = SwaggerConfig.INTERNAL_SERVER_ERROR_MESSAGE)

	})
	@PostMapping
	public ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtRequest tennisCourtRequest) {
		return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtRequest).getId()))
				.build();
	}

	/**
	 * To retrieve Tennis court by Id
	 * 
	 * @param tennisCourtId
	 */

	@ApiOperation(value = "Retrieve Tennis court by Id", tags = SwaggerConfig.TENNIS_COURTS_ENDPOINT)
	@ApiResponses({ @ApiResponse(code = SwaggerConfig.OK_CODE, message = SwaggerConfig.OK_MESSAGE),
			@ApiResponse(code = SwaggerConfig.BAD_REQUEST_CODE, message = SwaggerConfig.BAD_REQUEST_MESSAGE),
			@ApiResponse(code = SwaggerConfig.NOT_FOUND_CODE, message = SwaggerConfig.NOT_FOUND_MESSAGE),
			@ApiResponse(code = SwaggerConfig.INTERNAL_SERVER_ERROR_CODE, message = SwaggerConfig.INTERNAL_SERVER_ERROR_MESSAGE) })
	@GetMapping("/{id}")
	public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable("id") Long tennisCourtId) {
		return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
	}

	/**
	 * To retrieve Tennis court with schedules by Id
	 * 
	 * @param tennisCourtDTO,
	 *            request info
	 */

	@ApiOperation(value = "Retrieve Tennis court with schedules by Id", tags = SwaggerConfig.TENNIS_COURTS_ENDPOINT)
	@ApiResponses({ @ApiResponse(code = SwaggerConfig.OK_CODE, message = SwaggerConfig.OK_MESSAGE),
			@ApiResponse(code = SwaggerConfig.BAD_REQUEST_CODE, message = SwaggerConfig.BAD_REQUEST_MESSAGE),
			@ApiResponse(code = SwaggerConfig.NOT_FOUND_CODE, message = SwaggerConfig.NOT_FOUND_MESSAGE),
			@ApiResponse(code = SwaggerConfig.INTERNAL_SERVER_ERROR_CODE, message = SwaggerConfig.INTERNAL_SERVER_ERROR_MESSAGE) })
	@GetMapping("/{id}/schedules")
	public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(Long tennisCourtId) {
		return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
	}
}
