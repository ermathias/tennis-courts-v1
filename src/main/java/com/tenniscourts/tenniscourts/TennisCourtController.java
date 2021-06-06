package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;


import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/tennisCourt")
public class TennisCourtController extends BaseRestController {

    @Autowired
    TennisCourtService tennisCourtService;

    @ApiOperation(value = "Add new tennisCourt", notes = "Provided the created url")
    @PostMapping(path = "/addTennisCourt")
    public ResponseEntity<?> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @ApiOperation(value = "Find Tennis court by Id", notes = "Specify the TennisCour Id")
    @GetMapping(path = "findTennisCourtById/{tennisCourtId}")
    public ResponseEntity<?> findTennisCourtById(@PathVariable("tennisCourtId") long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @ApiOperation(value = "Find schedule Tennis court by Id", notes = "Specify the TennisCour Id")
    @GetMapping(path = "findTennisCourtWithSchedulesById/{tennisCourtId}")
    public ResponseEntity<?> findTennisCourtWithSchedulesById(@PathVariable("tennisCourtId") long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId)); }

}
