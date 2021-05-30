package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.schedules.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/tenniscourts")
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;
    private final ScheduleService scheduleService;

    //TODO: implement swagger
    @GetMapping("/id/{tennisCourtId}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable ("tennisCourtId") Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    //TODO: implement swagger
    @GetMapping("/name/{tennisCourtName}")
    public ResponseEntity<List<TennisCourtDTO>> findTennisCourtByName(@PathVariable ("tennisCourtName") String tennisCourtName) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtByName(tennisCourtName));
    }

    //TODO: implement swagger
    @PostMapping
    public ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    //TODO: implement rest and swagger
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteTennisCourtById(@RequestParam(name = "scheduleId") Long scheduleId,
                                                   @RequestParam(name = "tennisCourtId") Long tennisCourtId ) {
        tennisCourtService.deleteTennisCourtById(scheduleId, tennisCourtId);
        return ResponseEntity.ok().build();
    }
}
