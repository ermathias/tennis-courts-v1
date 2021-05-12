package com.tenniscourts.schedules;

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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Api(value = "ScheduleController",description = "REST API related to Scheduler Module")
@RestController("/api/schedule")
public class ScheduleController extends BaseRestController {

	@Autowired
    private final ScheduleService scheduleService;
	
    public ScheduleController(ScheduleService scheduleService) {
		super();
		this.scheduleService = scheduleService;
	}
 
    @ApiOperation(value = "Add Schedule Tennis Court")
    @ApiResponses(value = {
    		@ApiResponse(code = 200,message = "Success|OK"),
    		@ApiResponse(code = 401,message = "not authorized"),
    		@ApiResponse(code = 403,message = "forbidden"),
    		@ApiResponse(code = 404,message = "not found")
    })
    @PostMapping(path="/schedules",consumes="application/json",produces="application/json")
    public ResponseEntity<Void> addScheduleTennisCourt(CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId())).build();
    }
 
    @ApiOperation(value = "find Schedule by Date")
    @ApiResponses(value = {
    		@ApiResponse(code = 200,message = "Success|OK"),
    		@ApiResponse(code = 401,message = "not authorized"),
    		@ApiResponse(code = 403,message = "forbidden"),
    		@ApiResponse(code = 404,message = "not found")
    })
    @GetMapping("/schedules/{scheduleByDate}")
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(LocalDate startDate,
                                                                  LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }
 
    @ApiOperation(value = "Find Schedule by Id")
    @ApiResponses(value = {
    		@ApiResponse(code = 200,message = "Success|OK"),
    		@ApiResponse(code = 401,message = "not authorized"),
    		@ApiResponse(code = 403,message = "forbidden"),
    		@ApiResponse(code = 404,message = "not found")
    })
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleDTO> findByScheduleId(Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
