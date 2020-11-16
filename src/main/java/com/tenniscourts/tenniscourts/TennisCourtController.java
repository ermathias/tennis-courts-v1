package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tennis-courts")
@AllArgsConstructor
public class TennisCourtController extends BaseRestController {

    @Autowired
    private final TennisCourtService tennisCourtService;

    @GetMapping
    @ApiOperation("Find all Tennis Courts")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    public Iterable<TennisCourtDTO> findAll(Pageable pageable) {
        return tennisCourtService.findAll(pageable);
    }

    @GetMapping("/name/{name}")
    @ApiOperation("Find a Tennis Court by name")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<List<TennisCourtDTO>> findTennisCourtByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtByName(name));
    }

    @PostMapping
    @ApiOperation("Create or update a Tennis Court")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Tennis Court successfully created or updated")})
    public ResponseEntity<Void> addTennisCourt(@Valid @RequestBody TennisCourtDTO dto) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(dto).getId())).build();
    }

    @GetMapping("/{id}")
    @ApiOperation("Find a Tennis Court by ID")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(id));
    }

    @GetMapping("/schedules/{tennisCourtId}")
    @ApiOperation("Find Schedules for a Tennis Court by ID")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(id));
    }

}
