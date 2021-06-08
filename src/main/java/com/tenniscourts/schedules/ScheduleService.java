package com.tenniscourts.schedules;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.BusinessException;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import com.tenniscourts.utils.Constants;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
    	Schedule schedule = scheduleMapper.map(createScheduleRequestDTO);
    	List<Schedule> schedules = scheduleRepository.checkTennisCourtAvailability(tennisCourtId, schedule.getStartDateTime());
    	if (!schedules.isEmpty()) {
    		throw new BusinessException("That court has been already scheduled at the selected hour");
    	}
    	return scheduleMapper.map(scheduleRepository.saveAndFlush(schedule));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        //TODO: implement
        return null;
    }
    
    public List<ScheduleDTO> findFreeSlots(Long tennisCourtId, String startDateStr, String endDateStr) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN);
    	LocalDateTime startDate = LocalDateTime.parse(startDateStr, formatter);
    	LocalDateTime endDate = LocalDateTime.parse(endDateStr, formatter);
    	LocalDateTime endDateTime = LocalDateTime.parse(startDateStr, formatter);
    	
    	List<Schedule> schedules = scheduleRepository.findByTennisCourt_IdAndStartDateTimeBetween(tennisCourtId, startDate, endDate);
    	List<ScheduleDTO> freeSlots = new ArrayList<>();
    	
    	Calendar calendarEndDate = Calendar.getInstance();
    	calendarEndDate.set(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth(), endDate.getHour(), endDate.getMinute());
    	
    	Calendar calendarEndDateTime = Calendar.getInstance();
    	calendarEndDateTime.set(endDateTime.getYear(), endDateTime.getMonthValue(), endDateTime.getDayOfMonth(), endDateTime.getHour(), endDateTime.getMinute());
    	while (calendarEndDateTime.getTimeInMillis() < calendarEndDate.getTimeInMillis()) {
    		LocalDateTime startDateTime = LocalDateTime.from(endDateTime);
    		LocalDateTime endDateTimePlus = endDateTime.plusHours(1);
    		if (calendarEndDateTime.get(Calendar.HOUR) >= 23) {
    			calendarEndDateTime.add(Calendar.DAY_OF_MONTH, 1);
    		}
    		calendarEndDateTime.add(Calendar.HOUR, 1);
    		
    		System.out.println(endDateTimePlus.getHour());
    		if (schedules.isEmpty()) {
    			addFreeSchedule(tennisCourtId, startDateTime, endDateTimePlus, freeSlots);
    		}
    		for (Schedule schedule : schedules) {
    			if (!endDateTimePlus.isAfter(schedule.getStartDateTime()) && !endDateTimePlus.isBefore(schedule.getEndDateTime())) {
    				addFreeSchedule(tennisCourtId, startDateTime, endDateTimePlus, freeSlots);
    			}
    		}
    	}
    	
    	return freeSlots;
    }
    
    private void addFreeSchedule(Long tennisCourtId, LocalDateTime startDateTime, LocalDateTime endDateTime, List<ScheduleDTO> freeSlots) {
		TennisCourtDTO tennisCourtDTO = new TennisCourtDTO();
		tennisCourtDTO.setId(tennisCourtId);
		freeSlots.add(new ScheduleDTO(null, tennisCourtDTO, tennisCourtDTO.getId(), startDateTime, endDateTime));
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        //TODO: implement
        return null;
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
