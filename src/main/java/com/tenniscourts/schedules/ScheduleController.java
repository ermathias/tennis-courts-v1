package com.tenniscourts.schedules;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class ScheduleController extends BaseRestController {

	@Autowired
    private ScheduleService scheduleService;

	@ApiOperation("Add Schedule for the given tennis id")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Schedule added to the given tennis court successfully") })
	@PostMapping("/addSchedule")
	public ResponseEntity<Void> addScheduleTennisCourt(
			@Valid @RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
		return ResponseEntity
				.created(locationByEntity(scheduleService
						.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId()))
				.build();
	}

	@ApiOperation("Find schedules")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "All the schedules fetched for the given period") })
	@GetMapping("/findSchedule/from/{startDate}/to/{endDate}")
	public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@PathVariable LocalDate startDate,
			@PathVariable LocalDate endDate) {
		return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)),
				LocalDateTime.of(endDate, LocalTime.of(23, 59))));
	}

	@ApiOperation("Find schedule details")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Schedule details retrieved") })
	@GetMapping("/findSchedule/{scheduleId}")
	public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable Long scheduleId) {
		return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
	}
}
