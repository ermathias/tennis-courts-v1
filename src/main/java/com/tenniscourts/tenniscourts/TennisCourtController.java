package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Api(value = "TennisCourtController", description = "Operations pertaining to Tennis Courts")
@RestController
@RequestMapping("/tennisCourt")
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @ApiOperation(value = "Add tennis court", response = ResponseEntity.class, tags = "Add tennis court")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @ApiOperation(value = "Get specific tennis court by Id", response = ResponseEntity.class, tags = "Find by Id")
    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable(value = "id") Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @ApiOperation(value = "Get specific tennis court schedules by Id ", response = ResponseEntity.class, tags = "Find tennis court with schedules by id")
    @RequestMapping(value = "/schedule/{id}", method = RequestMethod.GET)
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@PathVariable(value = "id") Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
