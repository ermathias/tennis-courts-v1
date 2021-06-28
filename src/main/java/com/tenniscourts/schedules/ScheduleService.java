package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.AlreadyExistsEntityException;
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

    private final TennisCourtRepository tennisCourtRepository;

    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(CreateScheduleRequestDTO createScheduleRequestDTO) {
        TennisCourt tennisCourt = tennisCourtRepository.findById(createScheduleRequestDTO.getTennisCourtId()).orElseThrow(() -> {
            throw new EntityNotFoundException("Tennis court not found.");
        });
        LocalDateTime standardizedStartDateTime = createScheduleRequestDTO.getStartDateTime().withMinute(0);
        long duplicates = scheduleRepository.countAllByStartDateTimeAndTennisCourt(standardizedStartDateTime, tennisCourt);
        if (duplicates > 0) {
            throw new AlreadyExistsEntityException("Schedule already exists.");
        }

        Schedule schedule = new Schedule();
        schedule.setStartDateTime(standardizedStartDateTime);
        schedule.setTennisCourt(tennisCourt);
        schedule.setEndDateTime(standardizedStartDateTime.plusHours(1));
        return scheduleMapper.map(scheduleRepository.saveAndFlush(schedule));

    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleMapper.map(scheduleRepository.findAllByStartDateTimeAfterAndEndDateTimeBefore(startDate, endDate));
    }

    public ScheduleDTO findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found");
        });
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
