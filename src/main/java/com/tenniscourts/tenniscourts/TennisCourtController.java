package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.config.swagger.SwaggerConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@Api(value = "TennisCourtController")
@RequestMapping("/tenniscourts")
@RestController
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    //TODO: implement rest and swagger
    @ApiOperation(value = "Add a tennis court", tags = "addTennisCourt")
    @PostMapping("/addTennisCourt")
    public ResponseEntity<Void> addTennisCourt(@Valid @RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    //TODO: implement rest and swagger
    @ApiOperation(value = "Find a tennis court by id", tags = "findTennisCourtById")
    @GetMapping("/findTennisCourtById/{tennisCourtId}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    //TODO: implement rest and swagger
    @ApiOperation(value = "Find a tennis court with schedules by id", tags = "findTennisCourtWithSchedulesById")
    @GetMapping("/findTennisCourtWithSchedulesById/{tennisCourtId}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
