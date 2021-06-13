package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/tennis-courts")
public class TennisCourtController extends BaseRestController {

    @Autowired
    private final TennisCourtService tennisCourtService;

    @PostMapping
    @ApiOperation("Create or Update Tennis Court")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Tennis Court created or updated with success") })
    public ResponseEntity<Void> addTennisCourt(@Valid @RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId()))
                .build();
    }

    @GetMapping("/{tennisCourtId}")
    @ApiOperation("Find Tennis Court by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok") })
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable("tennisCourtId") Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @GetMapping("with-schedules/{tennisCourtId}")
    @ApiOperation("Find schedules for Tennis Court by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok") })
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(
            @PathVariable("tennisCourtId") Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }

    @GetMapping("name/{name}")
    @ApiOperation("Find Tennis Court by name")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok") })
    public ResponseEntity<List<TennisCourtDTO>> findTennisCourtByName(@PathVariable("name") String tennisCourtName) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtByName(tennisCourtName));
    }

    @GetMapping
    @ApiOperation("Find all Tennis Court")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok") })
    public Iterable<TennisCourtDTO> findAll(Pageable pageable) {
        return tennisCourtService.findAll(pageable);
    }
}
