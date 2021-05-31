package com.tenniscourts.tenniscourts;

import static com.tenniscourts.utils.TennisCourtsConstraints.CREATE_PATH;
import static com.tenniscourts.utils.TennisCourtsConstraints.FINDBYID_PATH;
import static com.tenniscourts.utils.TennisCourtsConstraints.FIND_WITHSCHEDULE_BYID_PATH;
import static com.tenniscourts.utils.TennisCourtsConstraints.TENNISCOURT_API_PATH;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenniscourts.config.BaseRestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping(path = TENNISCOURT_API_PATH)
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @ApiResponse(code = 200, message = "Tennis court created")
    @PostMapping(CREATE_PATH)
    public ResponseEntity<Void> addTennisCourt(@Valid @RequestBody TennisCourtDTO tennisCourtDTO,  BindingResult bindingResult) {
    	validateInputs(bindingResult);
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

    @ApiResponses({
    	@ApiResponse(code = 200, message = "Tennis court found"),
   	 	@ApiResponse(code = 404, message = "Tennis court not found")
    })
    @GetMapping(FINDBYID_PATH + "/{tennisCourtId}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @ApiResponses({
    	@ApiResponse(code = 200, message = "Tennis court found"),
   	 	@ApiResponse(code = 404, message = "Tennis court not found")
    })
    @GetMapping(FIND_WITHSCHEDULE_BYID_PATH + "/{tennisCourtId}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
