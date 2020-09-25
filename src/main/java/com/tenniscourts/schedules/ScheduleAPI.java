package com.tenniscourts.schedules;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = {"Schedules API"})
interface ScheduleAPI {

	@ApiOperation("Creates a new schedule for a tennis court.")
    public ResponseEntity<Void> addScheduleTennisCourt(@ApiParam("Schedule information.") CreateScheduleRequestDTO createScheduleRequestDTO);

	@ApiOperation("Finds all schedules by their dates.")
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@ApiParam(value = "Initial date.", example = "2020-03-21") LocalDate startDate, 
    															  @ApiParam(value = "End date.", example = "2020-03-22") LocalDate endDate);
	@ApiOperation("Finds a schedule by its id.")
	@ApiResponses({@ApiResponse(code = 404, message = "Schedule not found.")})
    public ResponseEntity<ScheduleDTO> findByScheduleId(@ApiParam(value = "Schedule id.", example = "1") Long scheduleId);
}
