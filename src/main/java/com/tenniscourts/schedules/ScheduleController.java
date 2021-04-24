package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.exceptions.AdminConstraint;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("schedule")
@AllArgsConstructor
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;

    @ApiOperation("Adds time on the tennis court. Only admin can call this method.")
    @PostMapping(value = "addScheduleTennisCourt")
    public ResponseEntity<Void> addScheduleTennisCourt(@RequestHeader(defaultValue = "true", required = false)
                                                           @AdminConstraint String isAdmin,
                                                       CreateScheduleRequestDTO createScheduleRequestDTO) {
        // @formatter:off
        return ResponseEntity.created(
                locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO.getTennisCourtId(),
                        createScheduleRequestDTO).getId()))
                .build();
        // @formatter:on
    }

    @ApiOperation("Find schedules between two dates.")
    @GetMapping(value = "findSchedulesByDates")
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        // @formatter:off
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(
                LocalDateTime.of(startDate, LocalTime.of(0, 0)),
                LocalDateTime.of(endDate, LocalTime.of(23, 59))));
        // @formatter:on
    }

    @ApiOperation("Find schedules by ID.")
    @GetMapping(value = "findByScheduleId")
    public ResponseEntity<ScheduleDTO> findByScheduleId(Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }

    @ApiOperation(value = "All schedules on a single tennis court.")
    @GetMapping(value = "/getSchedulesByTennisCourt")
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByTennisCourt(Long tennisCourtId) {
        return ResponseEntity.ok(scheduleService.findSchedulesByTennisCourtId(tennisCourtId));
    }
}
