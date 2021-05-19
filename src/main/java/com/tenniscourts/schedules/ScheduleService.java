package com.tenniscourts.schedules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
    	ScheduleDTO scheduleDTO =  new ScheduleDTO();
    	scheduleDTO.setTennisCourtId(tennisCourtId);
    	scheduleDTO.setStartDateTime(createScheduleRequestDTO.getStartDateTime());
    	scheduleDTO.setEndDateTime(createScheduleRequestDTO.getEndDateTime());
    	return scheduleMapper.map(scheduleRepository.saveAndFlush(scheduleMapper.map(scheduleDTO)));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
    	return scheduleMapper.map(scheduleRepository.findSchedulesByStartDateTimeEndDateTime(startDate, endDate));
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
