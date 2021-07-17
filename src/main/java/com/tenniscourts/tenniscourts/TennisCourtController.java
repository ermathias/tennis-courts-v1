package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @RequestMapping(value = "/add-tennis-court", method = RequestMethod.POST)
    public ResponseEntity<Void> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @RequestMapping(value = "/find-tennis-court-by-id", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@RequestParam("tennisCourtId") Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @RequestMapping(value = "/find-tennis-court-with-schedules-by-id", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@RequestParam("tennisCourtId") Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
