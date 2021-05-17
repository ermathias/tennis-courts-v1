package com.tenniscourts.schedules;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import com.tenniscourts.tenniscourts.TennisCourtService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

   // private final TennisCourtService tennisCourtService;


    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        //TODO: implement addSchedule
//        TennisCourtDTO tennisCourtDTO = tennisCourtService.findTennisCourtById(tennisCourtId);
//        ScheduleDTO scheduleDTO = new ScheduleDTO();
//        scheduleDTO.setTennisCourtDTO(tennisCourtDTO);
//        scheduleDTO.setStartDateTime(createScheduleRequestDTO.startDateTime);
//        scheduleDTO.setEndDateTime(createScheduleRequestDTO.endDateTime);
//        scheduleDTO.setTennisCourtId(tennisCourtId);
//        return ScheduleMapper.map(scheduleRepository.saveAndFlush(ScheduleMapper.map(scheduleDTO)));
        return null;

    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleMapper.map(scheduleRepository.findByStartDateTimeAfterAndEndDateTimeBefore(startDate,endDate));
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found.");
        });
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
