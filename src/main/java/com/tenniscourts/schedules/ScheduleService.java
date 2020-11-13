package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final TennisCourtRepository tennisCourtRepository;

    private final GuestRepository guestRepository;

    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(Long userId, Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        //TODO: implement addSchedule
            Guest userAction = guestRepository.getOne(userId);
            if(userAction.isAdmin()){
                TennisCourt court = tennisCourtRepository.getOne(tennisCourtId);
                LocalDateTime endOfTime = LocalDateTime.of(createScheduleRequestDTO.getStartDateTime().toLocalDate(), createScheduleRequestDTO.getStartDateTime().toLocalTime()).plusHours(1);
                Schedule schedule = new Schedule(court, createScheduleRequestDTO.getStartDateTime(), endOfTime, null);
                 if(Objects.isNull(scheduleRepository.findByStartDateTime(createScheduleRequestDTO.getStartDateTime()))){
                    return scheduleMapper.map(scheduleRepository.saveAndFlush(schedule));
                }else {
                    throw new UnsupportedOperationException("This schedule already exist!!");
                }
            } else {
                throw new UnsupportedOperationException("Only admin users can create new schedules.");
            }
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        //TODO: implement
        List<Schedule> scheduleEntities = scheduleRepository.StartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqual(startDate, endDate);
        List<ScheduleDTO> scheduleDTOS = scheduleMapper.map(scheduleEntities);
        return scheduleDTOS;
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        //TODO: implement
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found.");
        });
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
