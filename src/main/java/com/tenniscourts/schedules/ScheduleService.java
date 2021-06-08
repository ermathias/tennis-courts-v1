package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.reservations.Reservation;
import com.tenniscourts.reservations.ReservationStatus;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtController;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        Schedule schedule = scheduleMapper.map(createScheduleRequestDTO);
        return scheduleMapper.map(scheduleRepository.saveAndFlush(schedule));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleMapper.map(scheduleRepository
                .findByStartDateTimeAfterAndEndDateTimeBefore(startDate, endDate));
    }

    public List<ScheduleDTO> findAvailableScheduleByDates(LocalDateTime startDate, LocalDateTime endDate) {
        List<Schedule> schedules = scheduleRepository
                .findByStartDateTimeAfterAndEndDateTimeBefore(startDate, endDate);
        List<Schedule> availableSchedules = schedules.stream()
                .filter(schedule -> availableSchedule(schedule))
                .collect(Collectors.toList());
        return scheduleMapper.map(availableSchedules);
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found.");
        });
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }

    public Schedule getSchedule(Long scheduleId){
        return scheduleRepository.findById(scheduleId).orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found.");
        });
    }

    public boolean availableSchedule(Schedule schedule)
    {
       return schedule.getReservations().stream()
                .noneMatch(reservation -> reservation.getReservationStatus().equals(ReservationStatus.READY_TO_PLAY));
    }

}
