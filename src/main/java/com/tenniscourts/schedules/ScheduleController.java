package com.tenniscourts.schedules;

import com.tenniscourts.UriConstants;
import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.glassfish.jersey.server.Uri;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = UriConstants.SCHEDULE_PATH)
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;

    @PostMapping
    @ApiOperation("Create a new schedule for a tennis court")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Schedule created successfully") })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addScheduleTennisCourt(@RequestBody @Valid CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId())).build();
    }

    @GetMapping(path = UriConstants.START_DATE_VARIABLE + UriConstants.END_DATE_VARIABLE)
    @ApiOperation("Find schedules between two dates")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@PathVariable LocalDate startDate,
                                                                  @PathVariable LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)),
                LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    @GetMapping(path = UriConstants.SCHEDULE_ID_VARIABLE )
    @ApiOperation("Find schedule by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
