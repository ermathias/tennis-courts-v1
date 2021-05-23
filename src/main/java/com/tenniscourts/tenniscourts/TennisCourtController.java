package com.tenniscourts.tenniscourts;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class TennisCourtController extends BaseRestController {

	@Autowired
    private TennisCourtService tennisCourtService;

	@ApiOperation("Add Tennis Court")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Tennis Court Added") })
	@PostMapping("/addCourt")
	public ResponseEntity<Void> addTennisCourt(@Valid @RequestBody TennisCourtDTO tennisCourtDTO) {
		return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId()))
				.build();
	}

	@ApiOperation("Find Tennis Court")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Tennis Court details fetched") })
	@GetMapping("/findCourt/{tennisCourtId}")
	public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable Long tennisCourtId) {
		return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
	}

    //TODO: implement rest and swagger
	@ApiOperation("Find Tennis Court details with schedule details")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Tennis Court details with schedule fetched") })
	@GetMapping("/findCourtWithSchedule/{tennisCourtId}")
	public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(Long tennisCourtId) {
		return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
	}
}
