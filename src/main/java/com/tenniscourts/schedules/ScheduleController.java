package com.tenniscourts.schedules;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.tenniscourts.TennisCourtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Controller
public class ScheduleController extends BaseRestController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    TennisCourtService tennisCserv;

    @Autowired
    ScheduleRepository scheduleRepository;

    //TODO: implement rest and swagger
    @PostMapping(value= "/addScheduleTennisCourt")
    @ResponseBody
    @Transactional
    public String addScheduleTennisCourt(@RequestBody String schedulJSon) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.s");
        mapper.setDateFormat(format);
        Schedule sch = mapper.readValue(schedulJSon, Schedule.class);
        scheduleRepository.save(sch);
        return "redirect:/";
    }
    @GetMapping("/getSchedule/{id}")
    @ResponseBody
    public String getSchedule(@PathVariable(value = "id") Long id) throws IOException {
        Schedule s = scheduleService.findScheduleId(id);
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(s);

        return jsonInString;
    }
//    public ResponseEntity<Void> addScheduleTennisCourt(CreateScheduleRequestDTO createScheduleRequestDTO) {
//        return ResponseEntity.created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO).getId())).build();
//
//   }

    //TODO: implement rest and swagger
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(LocalDate startDate,
                                                                  LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    //TODO: implement rest and swagger
    public ResponseEntity<ScheduleDTO> findByScheduleId(Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
