package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Api("Schedule")
@AllArgsConstructor
@RestController
@RequestMapping("/schedule")
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;

    @PostMapping(value = "/add")
    @ApiOperation(value="Add a schedule to a Tennis Court")
    @ApiResponse(code = 201, message = "Schedule created")
    public ResponseEntity<Void> addScheduleTennisCourt(@Valid @RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId())).build();
    }

    @GetMapping(value = "/find")
    @ApiOperation(value="Find a schedule by a date range - start and end date")
    @ApiResponse(code = 200, message = "Schedule found")
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@ApiParam(example = "2021-01-01") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                  @ApiParam(example = "2021-01-05") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    @GetMapping(value = "/find/{scheduleId}")
    @ApiOperation(value="Find a schedule by ID")
    @ApiResponse(code = 200, message = "Schedule found")
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable("scheduleId") Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
