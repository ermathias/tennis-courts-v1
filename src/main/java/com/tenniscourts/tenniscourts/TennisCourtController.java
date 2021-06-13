package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.tenniscourts.tenniscourts.TennisCourtControllerConstants.*;

@RestController
@RequestMapping(CONTEXT)
@AllArgsConstructor
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @ApiOperation(value = "Persists the given tennis court")
    @PutMapping
    public ResponseEntity<Void> addTennisCourt(@Valid CreateTennisCourtDTO createTennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(createTennisCourtDTO).getId())).build();
    }

    @ApiOperation(value = "Returns a tennis court by it id without its schedules", response = TennisCourtDTO.class)
    @GetMapping(path = ID_PARAM)
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @ApiOperation(value = "Returns a tennis court by it id with its schedules", response = TennisCourtDTO.class)
    @GetMapping(path = ID_PARAM + SCHEDULES)
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
