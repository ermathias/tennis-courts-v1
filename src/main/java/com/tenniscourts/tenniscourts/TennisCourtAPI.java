package com.tenniscourts.tenniscourts;

import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = {"Tennis Courts API"})
interface TennisCourtAPI {

	@ApiOperation("Creates a new tennis court.")
    public ResponseEntity<Void> addTennisCourt(@ApiParam("Tennis court information.") TennisCourtDTO tennisCourtDTO);

	@ApiOperation("Finds a tennis court by its id.")
	@ApiResponses({@ApiResponse(code = 404, message = "Tennis court not found.")})
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@ApiParam(value = "Tennis court id.", example = "1") Long tennisCourtId);

	@ApiOperation("Finds a tennis court with schedules by its id.")
	@ApiResponses({@ApiResponse(code = 404, message = "Tennis court not found.")})
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@ApiParam(value = "Tennis court id.", example = "1") Long tennisCourtId);
}