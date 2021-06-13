package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.tenniscourts.constants.GlobalConstants.DATE_FORMAT;
import static com.tenniscourts.schedules.ScheduleControllerConstants.*;

@RestController
@RequestMapping(CONTEXT)
@AllArgsConstructor
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;

    @ApiOperation(value = "Persists the given schedule")
    @PutMapping
    public ResponseEntity<Void> addScheduleTennisCourt(@RequestBody @Valid CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId())).build();
    }

    @ApiOperation(value = "Returns schedule's list by date interval", response = ScheduleDTO.class)
    @PostMapping(path = LIST_BY_DATES)
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@PathVariable @DateTimeFormat(pattern = DATE_FORMAT) LocalDate startDate,
                                                                  @PathVariable @DateTimeFormat(pattern = DATE_FORMAT) LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    @ApiOperation(value = "Returns a schedule by its id", response = ScheduleDTO.class)
    @GetMapping(path = SCHEDULE_ID)
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
