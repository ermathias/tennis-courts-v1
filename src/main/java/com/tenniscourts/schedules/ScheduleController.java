package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/schedules")
@Api(value = "Schedules API")
public class ScheduleController extends BaseRestController {

    @Autowired
    private final ScheduleService scheduleService;

    @Autowired
    private final ScheduleMapper scheduleMapper;

    @GetMapping
    @ApiOperation(value = "Returns a list containing all schedules.")
    public ResponseEntity<List<ScheduleDTO>> listAllSchedules() {
        return ResponseEntity.ok().body(scheduleService.findAllSchedules());
    }

    @GetMapping("/{scheduleId}")
    @ApiOperation(value = "Returns only one schedule entity identified by its unique id.")
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }

    @GetMapping("/free")
    @ApiOperation(value = "Returns a list containing all future schedule slots available for reservation.")
    public ResponseEntity<List<ScheduleDTO>> findFreeScheduleSlots() {
        return ResponseEntity.ok(scheduleMapper.map(scheduleService.findAllFreeScheduleSlots()));
    }

    @GetMapping("/{startDate}/{endDate}")
    @ApiOperation(value = "Returns all schedule slots in the specified date period")
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                  @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    @PostMapping
    @ApiOperation(value = "Creates a new schedule entity.")
    public ResponseEntity<Void> addSchedule(@RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId())).build();
    }

    @PutMapping("/{scheduleId}")
    @ApiOperation(value = "Changes a specific schedule's attributes based on the new data sent.")
    public ResponseEntity<ScheduleDTO> updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.ok().body(scheduleMapper.map(scheduleService.updateSchedule(scheduleId, scheduleDTO)));
    }

    @DeleteMapping("/{scheduleId}")
    @ApiOperation(value = "Deletes a specific schedule entity.")
    public ResponseEntity<Void> removeSchedule(@PathVariable Long scheduleId) {
        scheduleService.removeSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }
}
