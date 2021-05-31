package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@Api(value = "Tennis Court Controller")
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @PostMapping("/tennis-court")
    @ApiOperation(value = "Add tennis court")
    @ApiResponse(code = 201, message = "Created")
    public ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @GetMapping(value = "/tennis-court/{tennisCourtId}")
    @ApiOperation("Find a tennis court by id")
    @ApiResponse(code = 200, message = "Ok")
    @ApiParam(name = "includeSchedules", value = "Flag to include schedules related to the tennis court")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable Long tennisCourtId, @RequestParam(defaultValue = "false") boolean includeSchedules) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }
}