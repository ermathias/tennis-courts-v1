package com.tenniscourts.schedules;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Api(value = "", tags = "Schedules", description = "Schedules REST Endpoints")
@RequestMapping(value = "/schedules")
@Validated
public interface ScheduleAPIService {

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add a schedule for a tennis court", nickname = "addScheduleTennisCourt")
    default ResponseEntity<Void> addScheduleTennisCourt(
            @ApiParam(value = "Schedule data") @Valid @RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds schedules within a period", nickname = "findSchedulesByDates")
    default ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(
            @ApiParam(example = "2021-05-20T13:00") @RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @ApiParam(example = "2021-05-21T13:00") @RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(value = "/{scheduleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Finds schedules given an id", nickname = "findByScheduleId")
    default ResponseEntity<ScheduleDTO> findByScheduleId(
            @PathVariable Long scheduleId) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(value = "/available", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Returns all schedules available", nickname = "getAvailableSchedules")
    default ResponseEntity<List<ScheduleDTO>> getAvailableSchedules() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}
