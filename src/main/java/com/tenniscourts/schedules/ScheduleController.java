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

@RestController
@RequestMapping("/schedules")
@AllArgsConstructor
public class ScheduleController extends BaseRestController {

    @Autowired
    private final ScheduleService scheduleService;

    @GetMapping("/all")
    @ApiOperation("List all Tennis Court Schedules")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<List<ScheduleDTO>> findAllSchedules() {
        return ResponseEntity.ok(scheduleService.findAllSchedules());
    }

    @GetMapping("/next")
    @ApiOperation("List all the next Tennis Court Schedules")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<List<ScheduleDTO>> findAllNextSchedules() {
        return ResponseEntity.ok(scheduleService.findAllNextSchedules());
    }

    @GetMapping("/{scheduleId}")
    @ApiOperation("Find a Schedule by ID")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable("scheduleId") Long id) {
        return ResponseEntity.ok(scheduleService.findSchedule(id));
    }

    @PostMapping
    @ApiOperation("Create a new Tennis Court Schedule")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Schedule successfully created")})
    public ResponseEntity<Void> addScheduleTennisCourt(@Valid @RequestBody CreateScheduleRequestDTO dto) {
        return ResponseEntity.created(locationByEntity(scheduleService
                .addSchedule(dto.getTennisCourtId(), dto).getId())).build();
    }

    @GetMapping
    @ApiOperation("List all Tennis Court Schedules by date")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        return ResponseEntity.ok(scheduleService
                .findSchedulesByDates(LocalDateTime.of(start, LocalTime.of(0, 0)),
                        LocalDateTime.of(end, LocalTime.of(23, 59))));
    }

}
