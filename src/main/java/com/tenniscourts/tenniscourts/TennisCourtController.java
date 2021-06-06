package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/tennis_courts")
@CrossOrigin(value = "*")
@Api(value = "Tennis Courts API")
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @Autowired
    TennisCourtMapper TennisCourtMapper;

    @GetMapping
    @ApiOperation(value = "Returns a list containing all tennis courts.")
    public ResponseEntity<List<TennisCourt>> listAllTennisCourts() {
        return ResponseEntity.ok().body(tennisCourtService.listAllTennisCourts());
    }

    @GetMapping(value = "/{tennisCourtId}")
    @ApiOperation(value = "Returns only one tennis court identified by its unique id.")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @GetMapping(value = "/schedules/{tennisCourtId}")
    @ApiOperation(value = "Returns a list containing all available schedule slots for a specific tennis court.")
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }

    @PostMapping
    @ApiOperation(value = "Creates a new tennis court entity.")
    public ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @DeleteMapping("/{tennisCourtId}")
    @ApiOperation(value = "Deletes a specific tennis court entity.")
    public ResponseEntity<Void> removeTennisCourt(@PathVariable Long tennisCourtId) {
        tennisCourtService.removeTennisCourt(tennisCourtId);
        return ResponseEntity.noContent().build();
    }
}
