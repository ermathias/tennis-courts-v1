package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourtRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TennisCourtRepository tennisCourtRepository;
    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        val tennisCourt = tennisCourtRepository.findById(tennisCourtId).orElseThrow(() -> {
            throw new EntityNotFoundException("Tennis Court not found.");
        });
        val schedule = scheduleMapper.map(createScheduleRequestDTO);
        schedule.setTennisCourt(tennisCourt);
        schedule.setEndDateTime(createScheduleRequestDTO.getStartDateTime().plusHours(1));
        return scheduleMapper.map(scheduleRepository.saveAndFlush(schedule));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleMapper.map(scheduleRepository.findAllByStartDateTimeLessThanEqualAndEndDateTimeGreaterThanEqual(startDate, endDate));
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleMapper.map(scheduleRepository.findById(scheduleId).get());
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
