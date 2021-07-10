package com.tenniscourts.service;

import com.tenniscourts.dto.CreateScheduleRequestDTO;
import com.tenniscourts.dto.ScheduleDTO;
import com.tenniscourts.dto.TennisCourtDTO;
import com.tenniscourts.exceptions.BusinessException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.mapper.ScheduleMapper;
import com.tenniscourts.model.Schedule;
import com.tenniscourts.model.TennisCourt;
import com.tenniscourts.repository.ScheduleRepository;
import org.hibernate.query.criteria.internal.BasicPathUsageException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;


    public ScheduleService(final ScheduleRepository scheduleRepository,
                           final ScheduleMapper scheduleMapper){
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
    }

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        ScheduleDTO addedSchedule;

        List<Schedule> schedules =  scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId);
        List<ScheduleDTO> scheduleDTOList=  scheduleMapper.map(schedules);
        boolean tennisCourtIsFree = true;
        for (ScheduleDTO scheduleDTO : scheduleDTOList){
            if (scheduleDTO.getStartDateTime().isEqual(createScheduleRequestDTO.getStartDateTime())){
                tennisCourtIsFree = false;
            }
        }

        if (tennisCourtIsFree) {
            Schedule schedule = new Schedule();
            TennisCourt tennisCourt = new TennisCourt();
            tennisCourt.setId(tennisCourtId);
            schedule.setTennisCourt(tennisCourt);
            schedule.setStartDateTime(createScheduleRequestDTO.getStartDateTime());
            schedule.setEndDateTime(createScheduleRequestDTO.getStartDateTime().plusHours(1));
            addedSchedule = scheduleMapper.map(scheduleRepository.save(schedule));
        } else {
            throw new BusinessException("Tennis Court has been already booked for this date");
        }


        return addedSchedule;

    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {

        return scheduleMapper.map(scheduleRepository.findSchedulesBetweenDates(startDate,endDate));

    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found.");
        });
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
