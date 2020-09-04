package com.tenniscourts.schedules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        //TODO: implement addSchedule done?
    	return scheduleMapper.map(scheduleRepository.saveAndFlush(scheduleMapper.map(createScheduleRequestDTO)));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        //TODO: implement done?
        return scheduleRepository.findAllByStartDateTimeAndEndDateTime(startDate, endDate);
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        //TODO: implement done?
    	return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).<EntityNotFoundException>orElseThrow(() -> {
    		throw new EntityNotFoundException("Schedule not found.");
    	});
    }
    
    public Optional<Schedule> findScheduleObject(Long scheduleId) {
        //TODO: implement done?
    	return scheduleRepository.findById(scheduleId);
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
    
    public List<Schedule> findSchedules() {
        //TODO: implement done?
        return scheduleRepository.findAll();
    }
    
    public Optional<Schedule> findById(Long id) {
		return scheduleRepository.findById(id);
	}
}
