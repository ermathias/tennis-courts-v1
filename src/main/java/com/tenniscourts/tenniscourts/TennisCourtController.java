package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/courts")
@CrossOrigin(origins = "*")
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @Autowired
    public TennisCourtController(TennisCourtService tennisCourtService) {
        this.tennisCourtService = tennisCourtService;
    }

    @PostMapping
    @ApiOperation(value = "API operation that create a new tennis court.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Tennis court successfully created.")
    })
    public ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(
                tennisCourtService.addTennisCourt(tennisCourtDTO).getId()))
                .build();
    }

    @GetMapping("/{courtId}")
    @ApiOperation(value = "API operation that return a tennis court by ID number.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tennis court found."),
            @ApiResponse(code = 404, message = "Tennis court not found.")
    })
    public ResponseEntity<TennisCourtDTO> findGuestById(@PathVariable Long courtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(courtId));
    }

}