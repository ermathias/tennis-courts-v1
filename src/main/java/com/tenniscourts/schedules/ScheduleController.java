package com.tenniscourts.schedules;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("schedule")
@AllArgsConstructor
public class ScheduleController extends BaseRestController implements ScheduleControllerSwagger {

    private final ScheduleService scheduleService;

	@PostMapping
	@Override
	public ResponseEntity<Void> addScheduleTennisCourt(
		@Valid @RequestBody final CreateScheduleRequestDTO createScheduleRequestDTO) {
        Long idTennisCourt = createScheduleRequestDTO.getTennisCourtId();
		ScheduleDTO schedule = scheduleService.addSchedule(idTennisCourt, createScheduleRequestDTO);
		Long idSchedule = schedule.getId();
		URI response = locationByEntity(idSchedule);
		return ResponseEntity.created(response).build();
    }

	@PostMapping("batch")
	@Override
	public ResponseEntity<Collection<ScheduleDTO>> addScheduleTennisCourtBatch(
		@Valid @RequestBody final CreateScheduleRequestBatchDTO request) {
		LocalDateTime start = request.getStartDateTime();
		List<ScheduleDTO> response = request.getTennisCourtId()
			.parallelStream()
			.map(each -> {
				CreateScheduleRequestDTO dto = new CreateScheduleRequestDTO();
				dto.setStartDateTime(start);
				dto.setTennisCourtId(each);
				return scheduleService.addSchedule(each, dto);
			})
			.collect(Collectors.toList());
		return ResponseEntity.ok(response);
	}

    //TODO: implement rest and swagger
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(final LocalDate startDate,
                                                                  final LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    //TODO: implement rest and swagger
    public ResponseEntity<ScheduleDTO> findByScheduleId(final Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
