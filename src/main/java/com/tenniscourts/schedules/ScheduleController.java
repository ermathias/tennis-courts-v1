package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/schedules")
@CrossOrigin(origins = "*")
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    @ApiOperation(value = "API operation that create a new schedule.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Schedule successfully created.")
    })
    public ResponseEntity<Void> addScheduleTennisCourt(
            @RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(
                scheduleService.addSchedule(createScheduleRequestDTO).getId())).build();
    }

    @GetMapping("/{scheduleId}")
    @ApiOperation(value = "API operation that return a schedule by ID number.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Schedule found."),
            @ApiResponse(code = 404, message = "Schedule not found.")
    })
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findScheduleById(scheduleId));
    }

    @GetMapping("/court/{courtId}")
    @ApiOperation(value = "API operation that return a list of schedules by tennis court ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Schedule found."),
            @ApiResponse(code = 404, message = "Schedule not found.")
    })
    public ResponseEntity<List<ScheduleDTO>> findByScheduleCourtId(@PathVariable Long courtId) {
        return ResponseEntity.ok(scheduleService.findSchedulesByTennisCourtId(courtId));
    }

}
