package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/schedules")
public class ScheduleController extends BaseRestController {

    @Autowired
    private final ScheduleService scheduleService;

    @PostMapping
    @ApiOperation("Creating a new tennis court schedule")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Schedule created with success.") })
    public ResponseEntity<Void> addScheduleTennisCourt(
            @Valid @RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity
                .created(locationByEntity(scheduleService
                        .addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId()))
                .build();
    }

    @GetMapping
    @ApiOperation("Listing tennis court schedules by date")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok") })
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)),
                LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    @GetMapping("/all")
    @ApiOperation("Listing all tennis court schedules")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok") })
    public ResponseEntity<List<ScheduleDTO>> findAllSchedules() {
        return ResponseEntity.ok(scheduleService.findAllSchedules());
    }

    @GetMapping("/next")
    @ApiOperation("Listing next tennis court schedules")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok") })
    public ResponseEntity<List<ScheduleDTO>> findAllNextSchedules() {
        return ResponseEntity.ok(scheduleService.findAllNextSchedules());
    }

    @GetMapping("/{scheduleId}")
    @ApiOperation("Find scheduling by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok") })
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable("scheduleId") Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
