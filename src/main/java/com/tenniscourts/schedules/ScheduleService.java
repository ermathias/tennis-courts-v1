package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    @Autowired
    private final ScheduleRepository scheduleRepository;

    @Autowired
    private final TennisCourtRepository tennisCourtRepository;

    @Autowired
    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) throws EntityNotFoundException {
        Schedule newSchedule;
        try{
            if(!tennisCourtRepository.existsById(tennisCourtId)){
                throw new EntityNotFoundException("Tennis court does not exist");
            }
        } catch (Throwable t){
            throw new EntityNotFoundException(t.getMessage());
        }

        TennisCourt tennisCourt = tennisCourtRepository.findById(tennisCourtId).get();
        newSchedule = buildSchedule(tennisCourt, createScheduleRequestDTO);

        return scheduleMapper.map(scheduleRepository.save(newSchedule));
    }

    private Schedule buildSchedule(TennisCourt tennisCourt, CreateScheduleRequestDTO dto){
        final Long NUMBER_ONE = 1L;
        return new Schedule.ScheduleBuilder()
                .tennisCourt(tennisCourt)
                .startDateTime(dto.getStartDateTime())
                .endDateTime(dto.getStartDateTime().plusHours(NUMBER_ONE))
                .build();
    }

    public List<ScheduleDTO> findAllByStartDateTimeAndEndDateTime(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleMapper.map(scheduleRepository.findAllByStartDateTimeAndEndDateTime(startDate, endDate));
    }

    public ScheduleDTO findSchedule(Long scheduleId) throws EntityNotFoundException {
        try{
            if(!scheduleRepository.findById(scheduleId).isPresent())
                throw new EntityNotFoundException("Schedule does not exist");
        } catch (Throwable t) {
            throw new EntityNotFoundException(t.getMessage());
        }
        return scheduleMapper.map(scheduleRepository.findById(scheduleId).get());
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {

        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
