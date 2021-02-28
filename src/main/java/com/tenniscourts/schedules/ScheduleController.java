package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.inject.Inject;

@AllArgsConstructor
@RestController
@RequestMapping("/api/schedules")
@Api(description = "Schedules REST API")
@CrossOrigin
public class ScheduleController extends BaseRestController {

    @Inject
    private final ScheduleService scheduleService;

    @PostMapping
    @ApiOperation(value = "Add a schedule to a tennis court.")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "The schedule has been added to the tennis court"),
        @ApiResponse(code = 404, message = "The informed tennis court was not found.")
    })
    public ResponseEntity<Void> addScheduleTennisCourt(@RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId())).build();
    }

    @GetMapping(value = "/schedulesByDates")
    @ApiOperation(value = "Find schedules by the given a start and an end dates.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "A list of schedule objects between the informed dates.")
    })
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX)));
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Find a schedule by its id.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "A schedule object."),
        @ApiResponse(code = 404, message = "The informed schedule was not found.")
    })
    public ResponseEntity<ScheduleDTO> findByScheduleId(Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }

    @GetMapping(value = "/schedulesWithFreeTimeSlots/{scheduleDate}")
    @ApiOperation(value = "Find schedules with free time slots (ready for reservations) given a schedule date.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "A list of schedule objects which are ready for reservations in a given schedule date.")
    })
    public ResponseEntity<List<ScheduleDTO>> findSchedulesWithFreeTimeSlotsByScheduleDate(
            @PathVariable("scheduleDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate scheduleDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesWithFreeTimeSlotsByScheduleDate(scheduleDate));
    }
}
