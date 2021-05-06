package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping(path ="schedule/")
public class ScheduleController extends BaseRestController {

	@Autowired
    private final ScheduleService scheduleService;

	@ApiOperation(Value='addScheduleTennisCourt', tags = "addScheduleTennisCourt")
	@PostMapping(path ="/addScheduleTennisCourt")
    public ResponseEntity<Void> addScheduleTennisCourt(@Valid @RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId())).build();
    }

	@ApiOperation(Value='findSchedulesByDates',response = ScheduleDTO.class, tags = "findSchedulesByDates")
    @GetMapping("/findSchedulesByDates/{startDate,endDate}")
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@PathVariable("startDate") localDate startDate,
    		@PathVariable("startDate") LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }
	
	@ApiOperation(Value='findByScheduleId',response = ScheduleDTO.class, tags = "findByScheduleId")
    @GetMapping("/findByScheduleId/{scheduleId}")
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable("scheduleId") Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
