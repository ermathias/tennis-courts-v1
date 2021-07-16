package com.tenniscourts.controller;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.dto.TennisCourtDTO;
import com.tenniscourts.service.TennisCourtService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tenis-court")
@AllArgsConstructor
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @ApiOperation(value = "Add tennis court", notes = "This method add a tennis court")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tennis court added!"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Void> addTennisCourt(
            @ApiParam(
                    name = "tennis court entity",
                    type = "TennisCourtDTO",
                    value = "Model of tennis court that will be saved",
                    required = true)
            @RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @ApiOperation(value = "Get tennis court", notes = "This method retrieve a tennis court")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tennis court found!"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(
            @ApiParam(
                    name = "id",
                    type = "Integer",
                    value = "Id of tennis court",
                    example = "1",
                    required = true)
            @PathVariable("id") Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @ApiOperation(value = "Get tennis court schedules", notes = "This method retrieve tennis court schedules")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tennis court schedules found!"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/{id}/schedules")
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(
            @ApiParam(
                    name = "id",
                    type = "Integer",
                    value = "Id of tennis court",
                    example = "1",
                    required = true)
            @PathVariable("id") Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
