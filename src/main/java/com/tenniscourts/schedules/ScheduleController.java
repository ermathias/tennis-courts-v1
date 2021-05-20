package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
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

@Api(value = "ScheduleController")
@RequestMapping("/schedules")
@RestController
@AllArgsConstructor
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;

    //TODO: implement rest and swagger
    @ApiOperation(value = "Add a schedule for a tennis court", tags = "addScheduleTennisCourt")
    @PostMapping("/addScheduleTennisCourt")
    public ResponseEntity<Void> addScheduleTennisCourt(@Valid @RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId())).build();
    }

    //TODO: implement rest and swagger
    @ApiOperation(value = "Find schedules by date", tags = "findSchedulesByDates")
    @GetMapping("/findSchedulesByDates")
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                  @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    //TODO: implement rest and swagger
    @ApiOperation(value = "Find a schedule by id", tags = "findByScheduleId")
    @GetMapping("/findByScheduleId/{scheduleId}")
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }

    @ApiOperation(value = "Find free schedules", tags = "findFreeSchedules")
    @GetMapping("/findFreeSchedules")
    public ResponseEntity<List<ScheduleDTO>> findFreeSchedules() {
        return ResponseEntity.ok(scheduleService.findFreeSchedules());
    }
}
