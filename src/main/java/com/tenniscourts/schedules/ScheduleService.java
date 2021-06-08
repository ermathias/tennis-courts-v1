package com.tenniscourts.schedules;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.BusinessException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import com.tenniscourts.utils.Constants;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    //6. As a Tennis Court Admin, I want to be able to create schedule slots for a given tennis court
    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
    	Schedule schedule = scheduleMapper.map(createScheduleRequestDTO);
    	List<Schedule> schedules = scheduleRepository.checkTennisCourtAvailability(tennisCourtId, schedule.getStartDateTime(), schedule.getStartDateTime().plusMinutes(59));
    	if (!schedules.isEmpty()) {
    		throw new BusinessException("That court has been already scheduled at the selected hour");
    	}
    	return scheduleMapper.map(scheduleRepository.saveAndFlush(schedule));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        //TODO: implement
        return null;
    }
    
    //2. As a User I want to be able to see what time slots are free
    public List<ScheduleDTO> findFreeSlots(Long tennisCourtId, String startDateStr, String endDateStr) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN);
    	LocalDateTime startDate = LocalDateTime.parse(startDateStr, formatter);
    	LocalDateTime endDate = LocalDateTime.parse(endDateStr, formatter);
    	
    	if (startDate.isAfter(endDate)) {
    		throw new DataIntegrityViolationException("Start date cannot be higher than the end date.");
    	}
    	
    	List<Schedule> schedules = scheduleRepository.findByTennisCourt_IdAndStartDateTimeBetween(tennisCourtId, startDate, endDate);
    	List<ScheduleDTO> freeSlots = new ArrayList<>();
    	
    	Calendar calendarEndDate = Calendar.getInstance();
    	calendarEndDate.set(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth(), endDate.getHour(), endDate.getMinute());
    	
    	Calendar calendarStartDate = Calendar.getInstance();
    	calendarStartDate.set(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth(), startDate.getHour(), startDate.getMinute());
    	while (calendarStartDate.getTimeInMillis() < calendarEndDate.getTimeInMillis()) {
    		
	        LocalDateTime startDateTime = LocalDateTime.of(calendarStartDate.get(Calendar.YEAR), calendarStartDate.get(Calendar.MONTH), calendarStartDate.get(Calendar.DAY_OF_MONTH),
	        		calendarStartDate.get(Calendar.HOUR_OF_DAY), calendarStartDate.get(Calendar.MINUTE));
    		calendarStartDate.add(Calendar.HOUR_OF_DAY, 1);
    		LocalDateTime endDateTime = LocalDateTime.of(calendarStartDate.get(Calendar.YEAR), calendarStartDate.get(Calendar.MONTH), calendarStartDate.get(Calendar.DAY_OF_MONTH),
	        		calendarStartDate.get(Calendar.HOUR_OF_DAY), calendarStartDate.get(Calendar.MINUTE));
    		
    		if (calendarStartDate.get(Calendar.HOUR_OF_DAY) >= Constants.OPENING_TIME + 1 && calendarStartDate.get(Calendar.HOUR_OF_DAY) <= Constants.CLOSING_TIME) {
        		if (schedules.isEmpty()) {
        			addFreeSchedule(tennisCourtId, startDateTime, endDateTime, freeSlots);
        		}
        		if (isScheduleTimeAvailable(schedules, startDateTime, calendarStartDate)) {
        			addFreeSchedule(tennisCourtId, startDateTime, endDateTime, freeSlots);
        		}
    		}
    	}
    	
    	return freeSlots;
    }
    
    private boolean isScheduleTimeAvailable(List<Schedule> schedules, LocalDateTime startDateTime, Calendar calendar) {
    	boolean isAvailable = false;
		for (Schedule schedule : schedules) {
			if (startDateTime.isBefore(schedule.getStartDateTime().minusMinutes(59)) || startDateTime.isAfter(schedule.getEndDateTime().minusMinutes(1))) {
				isAvailable = true;
			} else {
				isAvailable = false;
				break;
			}
		}
		return isAvailable;
    }
    
    private void addFreeSchedule(Long tennisCourtId, LocalDateTime startDateTime, LocalDateTime endDateTime, List<ScheduleDTO> freeSlots) {
		TennisCourtDTO tennisCourtDTO = new TennisCourtDTO();
		tennisCourtDTO.setId(tennisCourtId);
		freeSlots.add(new ScheduleDTO(null, tennisCourtDTO, tennisCourtDTO.getId(), startDateTime, endDateTime));
    }
    
    public ScheduleDTO findDTOById(Long scheduleId) {
   	 return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found.");
        });
   }

    public Schedule findById(Long scheduleId) {
    	return scheduleRepository.findById(scheduleId).orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found.");
        });
    }
    
    public ScheduleDTO update(Schedule schedule) {
    	Schedule newSchedule = findById(schedule.getId());
    	newSchedule.setReservations(schedule.getReservations());
    	return scheduleMapper.map(scheduleRepository.saveAndFlush(newSchedule));
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
