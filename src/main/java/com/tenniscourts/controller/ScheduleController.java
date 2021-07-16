package com.tenniscourts.controller;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.dto.CreateScheduleRequestDTO;
import com.tenniscourts.dto.ScheduleDTO;
import com.tenniscourts.service.ScheduleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Add schedule to tennis court", notes = "This method add a schedule to tennis court")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Schedule added!"),
    })
    @PostMapping
    public ResponseEntity<Void> addScheduleTennisCourt(
            @ApiParam(
                    name = "Schedule entity",
                    type = "CreateScheduleRequestDTO",
                    value = "Model of a schedule of tennis court that will be saved",
                    required = true)
            @RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO).getId())).build();
    }

    @ApiOperation(value = "Get schedule of a tennis court by dates", notes = "This method retrieve a schedule")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Schedule found!"),
    })
    @GetMapping("/{startDate}/{endDate}")
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(
            @ApiParam(
                    name = "startDate",
                    type = "LocalDate",
                    value = "Start date",
                    example = "2020-12-20T20:00",
                    required = true)
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @ApiParam(
                    name = "endDate",
                    type = "LocalDate",
                    value = "End date",
                    example = "2020-02-20T21:00",
                    required = false)
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    @ApiOperation(value = "Get schedule of a tennis court by Id", notes = "This method retrieve a schedule")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Schedule found!"),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<ScheduleDTO> findByScheduleId(
            @ApiParam(
                    name = "id",
                    type = "Integer",
                    value = "Id of schedule",
                    example = "1",
                    required = true)
            @PathVariable("id") Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> findAll() {
        return ResponseEntity.ok(scheduleService.findAll());
    }
}
