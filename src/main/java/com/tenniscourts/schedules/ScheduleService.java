package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import com.tenniscourts.tenniscourts.TennisCourtMapper;
import com.tenniscourts.tenniscourts.TennisCourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Service
public class ScheduleService {

    
    @Autowired
    private  ScheduleRepository scheduleRepository;
    @Autowired
    private  ScheduleMapper scheduleMapper;
    @Autowired
    private  TennisCourtService tennisCourtService;
    @Autowired
    private  TennisCourtMapper tennisCourtMapper;
    @Autowired
    private ScheduleService scheduleService;


    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        if(createScheduleRequestDTO.getStartDateTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Cannot Schedule past dates.");
        }
        ScheduleDTO obj = new ScheduleDTO();
        TennisCourtDTO tennisDTO = tennisCourtService.findTennisCourtById(tennisCourtId);
        LocalDate dateStart = createScheduleRequestDTO.getStartDateTime().toLocalDate();
        LocalDate dateEnd = createScheduleRequestDTO.getStartDateTime().plusHours(1).toLocalDate();
        List<ScheduleDTO> schedules = findSchedulesByDatesAndCourtId(tennisCourtId,
                LocalDateTime.of(dateStart, LocalTime.of(0, 0)),
                LocalDateTime.of(dateEnd, LocalTime.of(23, 59)));

        if(!schedules.isEmpty()){
            for(ScheduleDTO schedule : schedules){
                if(createScheduleRequestDTO.getStartDateTime().isEqual(schedule.getStartDateTime())
                        || createScheduleRequestDTO.getStartDateTime().isAfter(schedule.getStartDateTime())
                        && createScheduleRequestDTO.getStartDateTime().isBefore(schedule.getEndDateTime())){
                    throw new IllegalArgumentException("Schedule already registered.");
                }
            }
        }
        obj.setTennisCourt(tennisDTO);
        obj.setTennisCourtId(tennisCourtId);
        obj.setStartDateTime(createScheduleRequestDTO.getStartDateTime());
        obj.setEndDateTime(createScheduleRequestDTO.getStartDateTime().plusHours(1));

        return scheduleMapper.map(scheduleRepository.saveAndFlush(scheduleMapper.map(obj)));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleMapper.map(scheduleRepository
                .findAllByStartDateTimeAfterAndEndDateTimeBefore(startDate,endDate));
    }

    public List<ScheduleDTO> findSchedulesByDatesAndCourtId(Long tennisCourtId, LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleMapper.map(scheduleRepository
                .findAllByTennisCourt_IdAndStartDateTimeAfterAndEndDateTimeBefore(tennisCourtId,startDate,endDate));
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleMapper.map(scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new EntityNotFoundException("Schedule not Found")));
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }


}
