package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    //TODO: implement rest and swagger
    @ApiOperation(value = "Insert new court. Parameter UserId is for checking user role. Only admin role can call this method")
    @PostMapping(value = "/addCourt")
    public ResponseEntity<Void> addTennisCourt(Long userId, TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(userId, tennisCourtDTO).getId())).build();
    }

    //TODO: implement rest and swagger
    @ApiOperation(value = "Find court by Id")
    @GetMapping(value = "/findCourt")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    //TODO: implement rest and swagger
    @ApiOperation(value = "Find all schedules for the given court")
    @GetMapping(value = "/findCourtWithSchedulesById")
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }

    @ApiOperation(value = "List All courts with free schedules available, also listing the schedules")
    @GetMapping(value = "/findCourtWithFreeSchedules")
    public ResponseEntity<List<TennisCourtDTO>> findTennisCourtWithFreeSchedules() {
        return ResponseEntity.ok(tennisCourtService.findAllCourtsWithFreeSchedules());
    }

    @ApiOperation(value = "List all application courts")
    @GetMapping(value = "/getallcourts")
    public ResponseEntity<List<TennisCourtDTO>> findAllTennisCourts(){
        return ResponseEntity.ok(tennisCourtService.findAllCourts());
    }

}
