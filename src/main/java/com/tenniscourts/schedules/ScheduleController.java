package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.exceptions.EntityNotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/schedule")
public class ScheduleController extends BaseRestController {

    @Autowired
    private final ScheduleService scheduleService;

    @ApiOperation("Adds a schedule for a tennis court")
    @PostMapping("add")
    public ResponseEntity<Void> addScheduleTennisCourt(@Valid @RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId())).build();
    }

    @ApiOperation("Search for a schedule based on the start and end date")
    @GetMapping(path = "bydate/{startDate}/{endDate}")
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@PathVariable("startDate") LocalDate startDate,
                                                                  @PathVariable("endDate") LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findAllByStartDateTimeAndEndDateTime(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    @ApiOperation("Search for a schedule by schedule id")
    @GetMapping(path = "id/{id}")
    public ResponseEntity<ScheduleDTO> findByScheduleId(@Valid @PathVariable("id") Long scheduleId) throws EntityNotFoundException {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
