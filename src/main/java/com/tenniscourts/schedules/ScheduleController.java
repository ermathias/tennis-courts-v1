package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;

    @PostMapping("/")
    public ResponseEntity<Void> addScheduleTennisCourt(@RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(scheduleDTO.getTennisCourtId(), scheduleDTO).getId())).build();
    }

    //TODO: implement rest and swagger
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(LocalDate startDate,
                                                                  LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDTO> findByScheduleId(Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
