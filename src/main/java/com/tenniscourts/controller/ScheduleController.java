package com.tenniscourts.controller;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.dto.CreateScheduleRequestDTO;
import com.tenniscourts.dto.ScheduleDTO;
import com.tenniscourts.service.ScheduleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;

    public ScheduleController(final ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @ApiOperation("Add schedule to tennis court")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "A schedule has been added successfully to a tennis court"),
            @ApiResponse(code = 400, message = "Bad request. Check your input"),
            @ApiResponse(code = 500, message = "An error has occurred in adding the schedule to a tennis court")
    })
    @RequestMapping(value = "/api/schedule/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addScheduleTennisCourt(@RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId())).build();
    }

    @ApiOperation("Get schedule by start end dates")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A schedule has been successfully retrieved"),
            @ApiResponse(code = 400, message = "Bad request. Check your input"),
            @ApiResponse(code = 500, message = "An error has occurred in getting a schedule by dates")
    })
    @RequestMapping(value = "/api/schedule/get-by-dates", method = RequestMethod.GET)
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@RequestParam
                                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate startDate,
                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    @ApiOperation("Get schedule by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A schedule has been successfully retrieved"),
            @ApiResponse(code = 400, message = "Bad request. Check your input"),
            @ApiResponse(code = 500, message = "An error has occurred in getting a schedule by ID")
    })
    @RequestMapping(value = "/api/schedule/get", method = RequestMethod.GET)
    public ResponseEntity<ScheduleDTO> findByScheduleId(@RequestParam Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }


    @ApiOperation("Find free slots")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A list of free slots has been successfully retrieved"),
            @ApiResponse(code = 400, message = "Bad request. Check your input"),
            @ApiResponse(code = 500, message = "An error has occurred in getting a list of free slots")
    })
    @RequestMapping(value = "/api/schedule/get-free-slots", method = RequestMethod.GET)
    public ResponseEntity<List<ScheduleDTO>> findFreeSlots() {
        return ResponseEntity.ok(scheduleService.findFreeSlots());
    }
}
