package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

@AllArgsConstructor
@RestController
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @RequestMapping(value = "/addTennisCourt", method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity<TennisCourtDTO> addTennisCourt(@RequestBody TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @RequestMapping(value = "/findTennisCourtById/{tennisCourtId}", method = RequestMethod.GET)
    @GetMapping
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @RequestMapping(value = "/findTennisCourtWithSchedulesById/{tennisCourtId}", method = RequestMethod.GET)
    @GetMapping
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
