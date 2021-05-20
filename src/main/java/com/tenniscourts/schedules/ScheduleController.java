package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Api(value = "ScheduleController", description = "Operations pertaining to scheduling tennis courts")
@RestController
@RequestMapping("/scheduleCourt")
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;

    @ApiOperation(value = "Schedule tennis court", response = ResponseEntity.class, tags = "Schedule tennis court")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Void> addScheduleTennisCourt(@RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO).getId())).build();
    }

    @ApiOperation(value = "Find schedules by Date", response = ResponseEntity.class, tags = "Get scheduled tennis court by Dates")
    @RequestMapping(value = "/find/{startDate}/{endDate}", method = RequestMethod.GET)
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@PathVariable(value = "startDate") LocalDate startDate,
                                                                  @PathVariable(value = "endDate")LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    @ApiOperation(value = "Get schedules by Id", response = ResponseEntity.class, tags = "Get schedules by Id")
    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable(value = "id") Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
