package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;

import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/tennisCourt")
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @PostMapping("/{tennisCourtDTO}")
    public ResponseEntity<Void> addTennisCourt(TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @GetMapping("/{tennisCourtId}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @GetMapping("/{scheduledId}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@PathVariable Long scheduledId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(scheduledId));
    }
}
