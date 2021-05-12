package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.BaseRestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "TennisCourtController",description = "REST API related to Tennis-Court Module")
@RestController("/api/court")
public class TennisCourtController extends BaseRestController {

	@Autowired
    private final TennisCourtService tennisCourtService;

    public TennisCourtController(TennisCourtService tennisCourtService) {
		super();
		this.tennisCourtService = tennisCourtService;
	}
 
    @ApiOperation(value = "Add Tennis Court")
    @ApiResponses(value = {
    		@ApiResponse(code = 200,message = "Success|OK"),
    		@ApiResponse(code = 401,message = "not authorized"),
    		@ApiResponse(code = 403,message = "forbidden"),
    		@ApiResponse(code = 404,message = "not found")
    })
    @PostMapping(path="/tennis-courts",consumes="application/json",produces="application/json")
    public ResponseEntity<Void> addTennisCourt(TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }
 
    @ApiOperation(value = "Find Tennis Court by Id")
    @ApiResponses(value = {
    		@ApiResponse(code = 200,message = "Success|OK"),
    		@ApiResponse(code = 401,message = "not authorized"),
    		@ApiResponse(code = 403,message = "forbidden"),
    		@ApiResponse(code = 404,message = "not found")
    })
    @GetMapping("/tennis-courts/{tennisCourtId}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }
 
    @ApiOperation(value = "Find Tennis Court schedule by Id")
    @ApiResponses(value = {
    		@ApiResponse(code = 200,message = "Success|OK"),
    		@ApiResponse(code = 401,message = "not authorized"),
    		@ApiResponse(code = 403,message = "forbidden"),
    		@ApiResponse(code = 404,message = "not found")
    })
    @GetMapping("/tennis-courts/schedule/{tennisCourtId}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
