package com.tenniscourts.tenniscourts;

import javax.inject.Inject;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@AllArgsConstructor
@RestController
@RequestMapping("/api/tennis-courts")
@Api(description = "Tennis Court REST API")
@CrossOrigin
public class TennisCourtController extends BaseRestController {

    @Inject
    private final TennisCourtService tennisCourtService;

    @PostMapping
    @ApiOperation(value = "Create a new tennis court.")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "The tennis court has been created.")
    })
    public ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Find a tennis court by its id.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The tennis court object."),
        @ApiResponse(code = 404, message = "The informed tennis court was not found.")
    })
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable("id") Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @GetMapping(value = "/withSchedules/{id}")
    @ApiOperation(value = "Find a tennis court with schedules by its id.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The tennis court object containing the schedules' list."),
        @ApiResponse(code = 404, message = "The informed tennis court was not found.")
    })
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@PathVariable("id") Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
