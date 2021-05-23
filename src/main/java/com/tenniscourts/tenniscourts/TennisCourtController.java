package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/tenniscourt")
public class TennisCourtController extends BaseRestController {

    @Autowired
    private final TennisCourtService tennisCourtService;

    @ApiOperation("Adds a tennis court to the database")
    @PostMapping("add")
    public ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }
    @ApiOperation("Get all the tennis courts registered in the database")
    @GetMapping
    public ResponseEntity<List<TennisCourt>> getAllTennisCourts() {
        return ResponseEntity.ok(tennisCourtService.getAllTennisCourts());
    }

    @ApiOperation("Get a tennis court by id")
    @GetMapping
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    //TODO: implement rest and swagger
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
