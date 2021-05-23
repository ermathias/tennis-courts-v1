package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    private final TennisCourtRepository tennisCourtRepository;

    private final TennisCourtMapper tennisCourtMapper;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        //TODO: implement addSchedule
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setTennisCourtId(tennisCourtId);
        scheduleDTO.setStartDateTime(createScheduleRequestDTO.getStartDateTime());
        scheduleDTO.setEndDateTime(createScheduleRequestDTO.getStartDateTime().plusHours(1));

        TennisCourtDTO tennisCourt = tennisCourtRepository.findById(tennisCourtId).map(tennisCourtMapper::map).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Tennis Court not found.");
        });
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        if (tennisCourt.getTennisCourtSchedules() != null && !tennisCourt.getTennisCourtSchedules().isEmpty())
            scheduleDTOS.addAll(tennisCourt.getTennisCourtSchedules());
        scheduleDTOS.add(scheduleDTO);
        tennisCourt.setTennisCourtSchedules(scheduleDTOS);
        scheduleDTO.setTennisCourt(tennisCourt);
        return scheduleMapper.map(scheduleRepository.saveAndFlush(scheduleMapper.map(scheduleDTO)));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        //TODO: implement
        return scheduleMapper.map(scheduleRepository.findByStartDateTimeAndEndDateTime(startDate, endDate));
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        //TODO: implement
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found.");
        });
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
