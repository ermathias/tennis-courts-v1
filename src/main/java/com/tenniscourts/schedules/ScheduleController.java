package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Api(value = "ScheduleController", tags = {"Schedule Tennis Courts"})
@RestController
@RequestMapping("/v1/schedule")
@AllArgsConstructor
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;

    @ApiOperation(httpMethod = "POST", value = "Get value",notes = "description")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request completed successfully"),
            @ApiResponse(code = 400, message = "There was a validation issue with the supplied request body"),
            @ApiResponse(code = 404, message = "No session was provided or could be found for the schedule"),
            @ApiResponse(code = 500, message = "Internal Error")})
    @PostMapping
    public ResponseEntity<Void> addScheduleTennisCourt(@RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(
                scheduleService.addSchedule(createScheduleRequestDTO.getTennisCourtId(),
                createScheduleRequestDTO).getId())).build();
    }

    @ApiOperation(httpMethod = "GET", value = "Get value",notes = "description")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK!"),
            @ApiResponse(code = 404, message = "Schedule item not found"),
            @ApiResponse(code = 500, message = "Internal Error")})
    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@RequestParam("startDate") LocalDate startDate,
                                                                  @RequestParam ("endDate") LocalDate endDate) {
        return ResponseEntity.ok(
                scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)),
                LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    @ApiOperation(httpMethod = "GET", value = "Get value",notes = "description")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK!"),
            @ApiResponse(code = 404, message = "Schedule item not found"),
            @ApiResponse(code = 500, message = "Internal Error")})
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDTO> findByScheduleId(@RequestParam("scheduleId") Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
