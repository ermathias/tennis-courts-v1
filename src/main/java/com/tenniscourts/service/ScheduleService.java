package com.tenniscourts.service;

import com.tenniscourts.exceptions.BusinessException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.model.Schedule;
import com.tenniscourts.repository.ScheduleRepository;
import com.tenniscourts.storage.CreateScheduleRequestDTO;
import com.tenniscourts.storage.ScheduleDTO;
import com.tenniscourts.storage.ScheduleMapper;
import com.tenniscourts.model.TennisCourt;
import com.tenniscourts.repository.TennisCourtRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final TennisCourtRepository tennisCourtRepository;
    final ZoneOffset zoneOffset = ZoneOffset.UTC;

    public ScheduleDTO addSchedule(@NonNull Long tennisCourtId, @NonNull CreateScheduleRequestDTO createScheduleRequestDTO) {

        TennisCourt tennisCourt = tennisCourtRepository.findById(tennisCourtId).orElseThrow(
                () -> new EntityNotFoundException("Tennis Court by id " + tennisCourtId + " was not founded!"));

        Schedule scheduleVerifyIsFree = scheduleRepository.findByTennisCourtIdAndStartDateTime(
                tennisCourt.getId(),createScheduleRequestDTO.getStartDateTime());

        if(scheduleVerifyIsFree != null){
            Schedule schedule = Schedule.builder()
                    .startDateTime(createScheduleRequestDTO.getStartDateTime())
                    .endDateTime(createScheduleRequestDTO.getStartDateTime().plusHours(1))
                    .tennisCourt(tennisCourt)
                    .build();
            return scheduleMapper.map(scheduleRepository.save(schedule));
        } else {
            log.warn("Schedule is already scheduled for this time!");
            throw new BusinessException("Tennis Court id " + tennisCourt + " is already scheduled for this time");
        }
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime initialDate, LocalDateTime endDate) {
        return scheduleMapper.map(scheduleRepository.findAllByStartDateTimeBetween(initialDate, endDate));
    }
    public ScheduleDTO findSchedule(@NonNull Long scheduleId) {
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule by id " + scheduleId + " was not founded!");
        });
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(@NonNull Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
