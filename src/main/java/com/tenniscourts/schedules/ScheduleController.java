package com.tenniscourts.schedules;

import static com.tenniscourts.utils.TennisCourtsConstraints.CREATE_PATH;
import static com.tenniscourts.utils.TennisCourtsConstraints.FINDBYDATES_PATH;
import static com.tenniscourts.utils.TennisCourtsConstraints.FINDBYID_PATH;
import static com.tenniscourts.utils.TennisCourtsConstraints.SCHEDULE_API_PATH;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping(path = SCHEDULE_API_PATH)
public class ScheduleController extends BaseRestController {

	private final ScheduleService scheduleService;

	@ApiResponse(code = 200, message = "Schedule created")
	@PostMapping(CREATE_PATH)
	public ResponseEntity<Void> addScheduleTennisCourt(
			@Valid @RequestBody CreateScheduleRequestDTO createScheduleRequestDTO, BindingResult bindingResult) {
		
		this.validateInputs(bindingResult);
		return ResponseEntity
				.created(locationByEntity(scheduleService
						.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId()))
				.build();
	}

	@GetMapping(FINDBYDATES_PATH)
	public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@Valid @RequestBody FindScheduleByDatesRequestDTO dto,
			BindingResult bindingResult) {
		
		this.validateInputs(bindingResult);
		return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(dto.getStartDate(), LocalTime.of(0, 0)),
				LocalDateTime.of(dto.getEndDate(), LocalTime.of(23, 59))));
	}

	@ApiResponses({
    	@ApiResponse(code = 200, message = "Schedule found"),
   	 	@ApiResponse(code = 404, message = "Schedule not found")
    })
    @GetMapping(FINDBYID_PATH + "/{scheduleId}")
	public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable Long scheduleId) {
		return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
	}
}
