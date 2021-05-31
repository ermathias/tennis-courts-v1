package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.tenniscourts.TennisCourtService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/schedules")
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Void> addScheduleTennisCourt(@RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(@RequestParam(name = "startDate") LocalDate startDate,
                                                                  @RequestParam(name = "endDate")LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    @GetMapping("/id/{scheduleId}")
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable ("scheduleId") Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteScheduleById (@PathVariable Long id) {
        scheduleService.deleteScheduleById(id);
        return ResponseEntity.ok().build();
    }
}
