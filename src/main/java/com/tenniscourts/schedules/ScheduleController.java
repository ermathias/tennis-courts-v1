package com.tenniscourts.schedules;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tenniscourts.config.BaseRestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("api/schedules")
public class ScheduleController extends BaseRestController {

	@Autowired
	private final ScheduleService scheduleService;

	@PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Void> addScheduleTennisCourt(@RequestBody @Valid CreateScheduleRequestDTO createScheduleRequestDTO) {
		return ResponseEntity
				.created(locationByEntity(scheduleService
						.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId()))
				.build();
	}

	@GetMapping("/findSchedulesByDates")
	public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@RequestParam(name = "startDate") @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
	@RequestParam(name = "endDate") @DateTimeFormat(iso = ISO.DATE) LocalDate endDate) {
		return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)),
				LocalDateTime.of(endDate, LocalTime.of(23, 59))));
	}

	@GetMapping("/findScheduleById/{scheduleId}")
	public ResponseEntity<ScheduleDTO> findByScheduleId(Long scheduleId) {
		return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
	}
}
