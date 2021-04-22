package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/tennisCourt")
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @PostMapping("/")
    @ApiOperation("Add a Tennis Court")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Add a Tennis Court") })
    public ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @GetMapping("/{tennisCourtId}")
    @ApiOperation("Find Tennis Court by ID")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Add a Tennis Court by ID") })
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @GetMapping("/{scheduledID}")
    @ApiOperation("Find Tennis Court by scheduledID")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Add a Tennis Court by scheduledID") })
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@PathVariable Long scheduledID) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(scheduledID));
    }
}
