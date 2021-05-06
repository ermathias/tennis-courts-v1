package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/tenniscourt")
public class TennisCourtController extends BaseRestController {

     @Autowired
	private final TennisCourtService tennisCourtService;

    //TODO: implement rest and swagger
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    //TODO: implement rest and swagger
    @GetMapping(value= "/{tenniscourtid}", consumes="*/*", produces = "application/json")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable("tenniscourtid")  Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    //TODO: implement rest and swagger
    @GetMapping(value= "/schedules/{tenniscourtid}", consumes="*/*", produces = "application/json")
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@PathVariable("tenniscourtid") Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
