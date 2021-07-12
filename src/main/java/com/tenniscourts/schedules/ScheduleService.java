package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    private final TennisCourtRepository tennisCourtRepository;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {

        TennisCourt court = tennisCourtRepository.findById(tennisCourtId).orElseThrow(() -> {
            throw new EntityNotFoundException("Tennis Court not found.");
        });

        Schedule schedule = Schedule.builder()
                .tennisCourt(court)
                .startDateTime(createScheduleRequestDTO.getStartDateTime())
                .endDateTime(createScheduleRequestDTO.getStartDateTime().plusHours(1L))
                .build();

        return scheduleMapper.map(this.scheduleRepository.saveAndFlush(schedule));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleMapper.map(scheduleRepository.findSchedulesBetweenDates(startDate, endDate));
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleMapper.map(scheduleRepository.findById(scheduleId)
                                                    .orElseThrow(() -> {
                                                        throw new EntityNotFoundException("Schedule not found.");
                                                    }));
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
