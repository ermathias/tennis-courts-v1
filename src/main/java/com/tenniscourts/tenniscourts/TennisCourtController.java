package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tennis-courts")
@AllArgsConstructor
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @PostMapping(value = "/")
    @ApiOperation("Add a tennis court")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created a tennis court")})
    public ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @GetMapping(value = "/{tennisCourtId}")
    @ApiOperation("Find tennis court by ID (light)")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Find tennis court by ID (light) - success")})
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @GetMapping(value = "/{tennisCourtId}/full")
    @ApiOperation("Find tennis court by ID (full)")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Find tennis court by ID (full) - success")})
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }

    // @GetMapping(value = "/")
    @ApiOperation("Find all tennis courts")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Find all tennis courts - success")})
    public ResponseEntity<List<TennisCourtDTO>> findAllGuests() {
        return ResponseEntity.ok(tennisCourtService.findAllTennisCourts());
    }
}
