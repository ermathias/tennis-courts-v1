package com.tenniscourts.tenniscourts;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    //TODO: implement rest and swagger
    @ApiOperation(value = "Add Tenis Court")
    @ApiResponses(value = {
    	    @ApiResponse(code = 200, message = "Add Tenis Court")})
    @PostMapping(value = "/tennis-court")
    public ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @ApiOperation(value = "Find Tennis Court by id")
    @ApiResponses(value = {
    	    @ApiResponse(code = 200, message = "Find Tennis Court by id")})
    //TODO: implement rest and swagger
    @GetMapping(value = "/tennis-court/{tennisCourtId}", produces = "application/json")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @ApiOperation(value = "Find Tennis Court by schedule")
    @ApiResponses(value = {
    	    @ApiResponse(code = 200, message = "Find Tennis Court with schedules by id")})
    //TODO: implement rest and swagger
    @GetMapping(value = "/tennis-court/{tennisCourtId}/scheduled/", produces = "application/json")
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
    
    //TODO: implement rest and swagger
    @ApiOperation(value = "Returns a full tennis courts list")
    @ApiResponses(value = {
    	    @ApiResponse(code = 200, message = "Returns the tennis courts list")})
    @GetMapping(value = "/tennis-courts", produces = "application/json")
    public ResponseEntity<List<TennisCourt>> findTennisCourts() {
        return ResponseEntity.ok(tennisCourtService.findTennisCourts());
    }
}
