package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Api(value = "ScheduleController", description = "REST APIs related to Schedule  service")
@AllArgsConstructor
@RestController
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;

    @ApiOperation(value = "Add schedules for Tennis Court", response = Void.class)
    @PostMapping("/schedule")

    public ResponseEntity<Void> addScheduleTennisCourt(Long tennisCourtId, String startDate, String endDate) {

        LocalDateTime sDate = LocalDateTime.parse(startDate);
        LocalDateTime eDate = LocalDateTime.parse(endDate);

        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(tennisCourtId,  CreateScheduleRequestDTO.builder().startDateTime(sDate).endDateTime(eDate).build()).getId())).build();

    }

    @ApiOperation(value = "Find Schedules by Dates", response = Iterable.class)
    @GetMapping("/schedulesbydates")
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(String startDate, String endDate) {
        LocalDate sDate = LocalDate.parse(startDate);
        LocalDate eDate = LocalDate.parse(endDate);
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(sDate, LocalTime.of(0, 0)), LocalDateTime.of(eDate, LocalTime.of(23, 59))));
    }

    @ApiOperation(value = "Finds Schedule by ID", response = ScheduleDTO.class)
    @GetMapping("/schedulesbyid")
    public ResponseEntity<ScheduleDTO> findByScheduleId(Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }

    @ApiOperation(value = "Find Schedules by Tennis court ID", response = Iterable.class)
    @GetMapping("/schedulesbycourtid")
    public ResponseEntity<List<ScheduleDTO>> schedulesByid(Long tid) {
        return ResponseEntity.ok(scheduleService.findSchedulesByTennisCourtId(tid));
    }

}
