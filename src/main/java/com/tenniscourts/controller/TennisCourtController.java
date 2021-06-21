package com.tenniscourts.controller;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.storage.TennisCourtDTO;
import com.tenniscourts.service.TennisCourtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(value = "TennisCourtController", tags = {"Tennis Courts"})
@RestController
@RequestMapping("/v1/tenniscourt")
@AllArgsConstructor
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @ApiOperation(httpMethod = "POST", value = "Post value",notes = "Include a new Tennis Court")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Request completed successfully"),
        @ApiResponse(code = 400, message = "There was a validation issue with the supplied request body"),
        @ApiResponse(code = 404, message = "No session was provided or could be found for the schedule"),
        @ApiResponse(code = 500, message = "Internal Error")})
    @PostMapping
    public ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @ApiOperation(httpMethod = "GET", value = "Get value",notes = "Get Tennis Court by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request completed successfully"),
            @ApiResponse(code = 400, message = "There was a validation issue with the supplied request body"),
            @ApiResponse(code = 404, message = "No session was provided or could be found for the tennis court"),
            @ApiResponse(code = 500, message = "Internal Error")})
    @GetMapping("/{tennisCourtId}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@RequestParam("tennisCourtId") Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @ApiOperation(httpMethod = "GET", value = "Get value",notes = "Get Tennis Court by Id and Schedule")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request completed successfully"),
            @ApiResponse(code = 400, message = "There was a validation issue with the supplied request body"),
            @ApiResponse(code = 404, message = "No session was provided or could be found for the tennis court"),
            @ApiResponse(code = 500, message = "Internal Error")})
    @GetMapping("/schedule/{id}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
