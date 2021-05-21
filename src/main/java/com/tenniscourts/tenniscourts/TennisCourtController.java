package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/tennis-courts")
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @PostMapping
    @ApiOperation("Creates a new tennis court")
    public ResponseEntity<Void> addTennisCourt(@RequestBody CreateTennisCourtRequestDTO createTennisCourtRequestDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(createTennisCourtRequestDTO).getId())).build();
    }

    @GetMapping(value = "/{tennisCourtId}")
    @ApiOperation("Fetches a tennis court by id")
    @ApiParam(name = "includeSchedules", value = "Flag to include schedules related to the tennis court")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable Long tennisCourtId, @RequestParam(defaultValue = "false") boolean includeSchedules) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId, includeSchedules));
    }
}
