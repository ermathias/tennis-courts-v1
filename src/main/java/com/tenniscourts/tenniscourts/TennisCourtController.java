package com.tenniscourts.tenniscourts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tenniscourts.config.BaseRestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TennisCourtController extends BaseRestController {

	@Autowired
    private final TennisCourtService tennisCourtService;

    @PostMapping("api/tenniscourt/add")
    public ResponseEntity<Void> addTennisCourt(@RequestBody	TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @GetMapping("api/tenniscourt/fetchById/{tennisCourtId}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @GetMapping("api/tenniscourt/fetchCourtWithSchedules/{tennisCourtId}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
