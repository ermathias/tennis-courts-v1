package com.tenniscourts.schedules;

import java.util.Collection;

import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(
	description = "API responsable for maintannce schedules")
public interface ScheduleControllerSwagger {

	@ApiOperation(
		value = "add schedule for one tennis court")
	@ApiResponses(
		value = {
			@ApiResponse(
				code = 200,
				message = "Return URI for created resource"),
			@ApiResponse(
				code = 400,
				message = "Invalid input"),
			@ApiResponse(
				code = 500,
				message = "Generic error"),
		})
	ResponseEntity<Void> addScheduleTennisCourt(
		@ApiParam(
			value = "A JSON value representing all information to create a schedule in batch.",
			example = "{\"tennisCourtId\": 2,\"startDateTime\": \"2000-01-01T00:00\"}") CreateScheduleRequestDTO createScheduleRequestDTO);

	@ApiOperation(
		value = "add schedule for one or more tennis court")
	@ApiResponses(
		value = {
			@ApiResponse(
				code = 200,
				message = "Return representation for created resource"),
			@ApiResponse(
				code = 400,
				message = "Invalid input"),
			@ApiResponse(
				code = 500,
				message = "Generic error"),
		})
	ResponseEntity<Collection<ScheduleDTO>> addScheduleTennisCourtBatch(
		@ApiParam(
			value = "A JSON value representing all information to create a schedule in batch.",
			example = "{\"tennisCourtId\": [2,3],\"startDateTime\": \"2000-01-01T00:00\"}") CreateScheduleRequestBatchDTO request);
}
