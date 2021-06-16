package com.tenniscourts.tenniscourts;

import com.tenniscourts.UriConstants;
import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(path = UriConstants.TENNIS_COURT_PATH)
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @PostMapping
    @ApiOperation("Create a new tennis court")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Tennis court created successfully") })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addTennisCourt(@RequestBody @Valid TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @GetMapping(path = UriConstants.TENNIS_COURT_ID_VARIABLE)
    @ApiOperation("Find an existing tennis court by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @GetMapping(path = UriConstants.WITH_SCHEDULES_PATH + UriConstants.TENNIS_COURT_ID_VARIABLE)
    @ApiOperation("Find an existing tennis court with schedules by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
