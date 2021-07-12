package com.tenniscourts.schedules;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ScheduleController extends BaseRestController {

	private final ScheduleService scheduleService;

	// TODO: implement rest and swagger
	public ResponseEntity<Void> addScheduleTennisCourt(CreateScheduleRequestDTO createScheduleRequestDTO) {
		return ResponseEntity
				.created(locationByEntity(scheduleService
						.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId()))
				.build();
	}

	// TODO: implement rest and swagger
	@PostMapping(value = "/v1/schedule/findSchedulesByDates")
	public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@RequestBody RangeDTO rangeDTO) {
		return ResponseEntity
				.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(rangeDTO.getStartDate(), LocalTime.of(0, 0)),
						LocalDateTime.of(rangeDTO.getEndDate(), LocalTime.of(23, 59))));
	}

	// TODO: implement rest and swagger
	@GetMapping(value = "/v1/schedule/{scheduleId}/retrieve")
	public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable Long scheduleId) {
		return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
	}
}
