package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.tenniscourts.TennisCourtDTO;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/schedule")
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;

    @ApiOperation(value = "Add schedule Tennis Court", notes = "Provided the schedule Tennis Court url")
    @PostMapping(path = "/addScheduleTennisCourt")
    public ResponseEntity<?> addScheduleTennisCourt(@RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(scheduleDTO).getId())).build();
    }

    @ApiOperation(value = "Find schedule Tennis Court by start date and end date", notes = "Specify the start date and end date")
    @GetMapping(path = "findSchedulesByDates/startDate/{startDate}/endDate/{endDate}")
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@PathVariable("startDate") LocalDate startDate,
                                                                  @PathVariable("endDate") LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)),
                LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    @ApiOperation(value = "Find Tennis Court by scheduleId", notes = "Find Tennis Court by scheduleId like 1001")
    @GetMapping(path = "findByScheduleId/{scheduleId}")
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable("scheduleId")Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
