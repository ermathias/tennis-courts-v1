package com.tenniscourts.schedules;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Schedule Controller
 */

@RestController
@RequestMapping("/schedules")
@Api(value = SwaggerConfig.SCHEDULER_VALUE, tags = SwaggerConfig.SCHEDULER_ENDPOINT)
@AllArgsConstructor
@Slf4j
public class ScheduleController extends BaseRestController {

	private final ScheduleService scheduleService;

	/**
	 * To add schedule
	 * 
	 * @param createScheduleRequestDTO,
	 *            request info
	 */

	@ApiOperation(value = "Add schedule", tags = SwaggerConfig.SCHEDULER_ENDPOINT)
	@ApiResponses({ @ApiResponse(code = SwaggerConfig.CREATED_CODE, message = SwaggerConfig.CREATED_MESSAGE),
			@ApiResponse(code = SwaggerConfig.BAD_REQUEST_CODE, message = SwaggerConfig.BAD_REQUEST_MESSAGE),
			@ApiResponse(code = SwaggerConfig.INTERNAL_SERVER_ERROR_CODE, message = SwaggerConfig.INTERNAL_SERVER_ERROR_MESSAGE)

	})
	@PostMapping
	public ResponseEntity<Void> addScheduleTennisCourt(@RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
		log.info("Started adding schedule");
		return ResponseEntity
				.created(locationByEntity(scheduleService
						.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId()))
				.build();
	}

	/**
	 * To retrieve reservation by date
	 * 
	 * @param startDate
	 * @param endDate
	 * @return List<ScheduleDTO>
	 */

	@ApiOperation(value = "Retrieve schedules by date", tags = SwaggerConfig.SCHEDULER_ENDPOINT)
	@ApiResponses({ @ApiResponse(code = SwaggerConfig.OK_CODE, message = SwaggerConfig.OK_MESSAGE),
			@ApiResponse(code = SwaggerConfig.BAD_REQUEST_CODE, message = SwaggerConfig.BAD_REQUEST_MESSAGE),
			@ApiResponse(code = SwaggerConfig.NOT_FOUND_CODE, message = SwaggerConfig.NOT_FOUND_MESSAGE),
			@ApiResponse(code = SwaggerConfig.INTERNAL_SERVER_ERROR_CODE, message = SwaggerConfig.INTERNAL_SERVER_ERROR_MESSAGE) })
	@GetMapping
	public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
		log.info("Started finding schedule");
		return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.parse(startDate.toString()),
				LocalDateTime.parse(endDate.toString())));

	}

	/**
	 * To retrieve schedules by Id
	 * 
	 * @param scheduleId
	 * @return {@link ScheduleDTO}
	 */

	@ApiOperation(value = "Retrieve schedules by id", tags = SwaggerConfig.SCHEDULER_ENDPOINT)
	@ApiResponses({ @ApiResponse(code = SwaggerConfig.OK_CODE, message = SwaggerConfig.OK_MESSAGE),
			@ApiResponse(code = SwaggerConfig.BAD_REQUEST_CODE, message = SwaggerConfig.BAD_REQUEST_MESSAGE),
			@ApiResponse(code = SwaggerConfig.NOT_FOUND_CODE, message = SwaggerConfig.NOT_FOUND_MESSAGE),
			@ApiResponse(code = SwaggerConfig.INTERNAL_SERVER_ERROR_CODE, message = SwaggerConfig.INTERNAL_SERVER_ERROR_MESSAGE) })
	@GetMapping("/{id}")
	public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable("id") Long scheduleId) {
		log.info("Started finding schedule");
		return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
	}
}
