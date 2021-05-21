package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TennisCourtRepository tennisCourtRepository;
    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(CreateScheduleRequestDTO createScheduleRequestDTO) {
        Assert.isTrue(createScheduleRequestDTO.getStartDateTime().isAfter(LocalDateTime.now()), "Cannot create a retroactive schedule.");
        TennisCourt tennisCourt = tennisCourtRepository.getOne(createScheduleRequestDTO.getTennisCourtId());
        return scheduleMapper.map(scheduleRepository.saveAndFlush(new Schedule(tennisCourt, createScheduleRequestDTO.getStartDateTime())));
    }

    public List<ScheduleDTO> findAvailableSchedules() {
        return scheduleMapper.map(scheduleRepository.findAllAvailable());
    }

    // this method could be merged with above if a better dynamic querying strategy was available
    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleMapper.map(scheduleRepository.findByStartDateTimeBetween(startDate, endDate));
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found."));
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
