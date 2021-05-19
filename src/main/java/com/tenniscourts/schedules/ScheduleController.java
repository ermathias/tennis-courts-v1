package com.tenniscourts.schedules;

import org.springframework.beans.factory.annotation.Autowired;
import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
public class ScheduleController extends BaseRestController {

	@Autowired
	private final ScheduleService scheduleService;

	@PostMapping("api/schedules/add")
	public ResponseEntity<Void> addScheduleTennisCourt(@RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
		return ResponseEntity
				.created(locationByEntity(scheduleService
						.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId()))
				.build();
	}

	@GetMapping("api/schedules/findSchedulesByDates/{startDate}/{endDate}")
	public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@PathVariable LocalDate startDate,
			@PathVariable LocalDate endDate) {
		return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)),
				LocalDateTime.of(endDate, LocalTime.of(23, 59))));
	}

	@GetMapping("api/schedules/findScheduleById/{scheduleId}")
	public ResponseEntity<ScheduleDTO> findByScheduleId(Long scheduleId) {
		return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
	}
}
