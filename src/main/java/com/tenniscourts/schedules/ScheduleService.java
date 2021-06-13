package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourtRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    @Autowired
    private final ScheduleRepository scheduleRepository;

    @Autowired
    private final ScheduleMapper scheduleMapper;

    @Autowired
    private final TennisCourtRepository tennisCourtRepository;

    @Transactional(rollbackFor = Exception.class)
    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        try {
            if (!tennisCourtRepository.findById(tennisCourtId).isPresent()) {
                throw new EntityNotFoundException("Tennis court not exists.");
            }
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        Schedule newSchedule = Schedule.builder().tennisCourt(tennisCourtRepository.findById(tennisCourtId).get())
                .startDateTime(createScheduleRequestDTO.getStartDateTime())
                .endDateTime(createScheduleRequestDTO.getStartDateTime().plusHours(1L)).build();
        return scheduleMapper.map(this.scheduleRepository.save(newSchedule));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleMapper.map(scheduleRepository
                .findAllByStartDateTimeLessThanEqualAndEndDateTimeGreaterThanEqual(endDate, startDate));
    }

    public List<ScheduleDTO> findAllSchedules() {
        return scheduleMapper.map(scheduleRepository.findAll());
    }

    public List<ScheduleDTO> findAllNextSchedules() {
        return scheduleMapper.map(scheduleRepository.findAllByStartDateTimeGreaterThanEqual(LocalDateTime.now()));
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleMapper.map(scheduleRepository.findById(scheduleId).get());
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
