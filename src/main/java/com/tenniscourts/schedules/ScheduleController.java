package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@AllArgsConstructor
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;

    @RequestMapping(value = "/addScheduleTennisCourt", method = RequestMethod.POST)
    public ResponseEntity<Void> addScheduleTennisCourt(@RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId())).build();
    }

    @RequestMapping(value = "/findSchedulesByDates", method = RequestMethod.GET)
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(
            @RequestParam @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    @RequestMapping(value = "/findAvailableScheduleByDates", method = RequestMethod.GET)
    public ResponseEntity<List<ScheduleDTO>> findAvailableScheduleByDates(
            @RequestParam @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findAvailableScheduleByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    @RequestMapping(value = "/findByScheduleId/{scheduleId}", method = RequestMethod.GET)
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
