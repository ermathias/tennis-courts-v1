package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
@AllArgsConstructor
@RestController
@RequestMapping("tennisCourt")
public class TennisCourtController extends BaseRestController {
	@Autowired
    private final TennisCourtService tennisCourtService;

    @ApiOperation("Add a Tennis Court")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Tennis Court Added")
    })
    @PostMapping("")
    public ResponseEntity<Void> addTennisCourt(@Valid @RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @ApiOperation("Find tennis Court By Id")
    @GetMapping("/{tennisCourtId}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @ApiOperation("Find tennis Court By Id and show Schedules")
    @GetMapping("/{tennisCourtId}/withSchedule")
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
