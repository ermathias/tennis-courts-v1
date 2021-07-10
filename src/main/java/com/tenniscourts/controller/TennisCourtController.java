package com.tenniscourts.controller;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.dto.TennisCourtDTO;
import com.tenniscourts.service.ScheduleService;
import com.tenniscourts.service.TennisCourtService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    public TennisCourtController(final TennisCourtService tennisCourtService){
        this.tennisCourtService = tennisCourtService;
    }


    @ApiOperation("Add tennis court")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Create tennis court"),
            @ApiResponse(code = 400, message = "Bad request. Check your input"),
            @ApiResponse(code = 500, message = "An error has occurred in creating a tennis court")
    })
    @RequestMapping(value = "/api/tennis-court/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @ApiOperation("Get tennis court")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get tennis court"),
            @ApiResponse(code = 400, message = "Bad request. Check your input"),
            @ApiResponse(code = 500, message = "An error has occurred in getting a tennis court")
    })
    @RequestMapping(value = "/api/tennis-court/get", method = RequestMethod.GET)
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@RequestParam Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @ApiOperation("Get tennis court with schedules")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Create tennis court"),
            @ApiResponse(code = 400, message = "Bad request. Check your input"),
            @ApiResponse(code = 500, message = "An error has occurred in creating a tennis court")
    })
    @RequestMapping(value = "/api/tennis-court/get-schedules", method = RequestMethod.GET)
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@RequestParam Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
