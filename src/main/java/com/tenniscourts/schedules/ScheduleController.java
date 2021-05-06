package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import com.tenniscourts.tenniscourts.TennisCourtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
public class ScheduleController extends BaseRestController {
    @Autowired
    private final ScheduleService scheduleService;
	@Autowired
    private final TennisCourtService tennisCourtService;

    //TODO: implement rest and swagger
    @PostMapping(value = "/addscheduletenniscourt", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> addScheduleTennisCourt(@RequestBody ScheduleDTO scheduleDTO) {
        TennisCourtDTO tennisCourtDTO = tennisCourtService.findTennisCourtById(scheduleDTO.getTennisCourtId());
        scheduleDTO.setTennisCourt(tennisCourtDTO);
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(scheduleDTO.getTennisCourtId(), scheduleDTO).getId())).build();
    }

    //TODO: implement rest and swagger
    @GetMapping(value= "/findschedulebydates", consumes="*/*", produces = "application/json")
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@RequestParam("startdate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                  @RequestParam("enddate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd")  LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    //TODO: implement rest and swagger
    @GetMapping(value= "/findschedulebyscheduleid/{sceduleid}", consumes="*/*", produces = "application/json")
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable("sceduleid") Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
