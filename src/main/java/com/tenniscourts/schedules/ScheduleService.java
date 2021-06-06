package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.BusinessException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.reservations.Reservation;
import com.tenniscourts.reservations.ReservationStatus;
import com.tenniscourts.tenniscourts.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Service
@AllArgsConstructor
public class ScheduleService {

    @Autowired
    private final ScheduleRepository scheduleRepository;

    @Autowired
    private final ScheduleMapper scheduleMapper;

    @Autowired
    private final TennisCourtRepository tennisCourtRepository;

    @Autowired
    private final TennisCourtMapper tennisCourtMapper;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {

        LocalDateTime startTime = createScheduleRequestDTO.getStartDateTime();
        LocalDateTime endTime = startTime.plusHours(1);

        Schedule existingSchedule = scheduleRepository.findExistingSchedule(tennisCourtId, startTime, endTime);

        if (existingSchedule != null) {
            throw new BusinessException("Time conflict exists for chosen schedule slot.");
        }

        Schedule newSchedule = new Schedule();
        newSchedule.setTennisCourt(tennisCourtRepository.findFirstById(tennisCourtId));
        newSchedule.setStartDateTime(startTime);
        newSchedule.setEndDateTime(endTime);
        newSchedule.setReservations(null);

        return scheduleMapper.map(scheduleRepository.saveAndFlush(newSchedule));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleMapper.map(scheduleRepository.findByStartDateTimeBetween(startDate, endDate));
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        Schedule existingSchedule = scheduleRepository.findFirstById(scheduleId);

        if (existingSchedule == null) {
            throw new EntityNotFoundException("Schedule not found");
        }

        return scheduleMapper.map(existingSchedule);
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }

    public Schedule updateSchedule(Long scheduleId, ScheduleDTO scheduleDTO) {

        Schedule existingSchedule = scheduleRepository.findFirstById(scheduleId);

        if (existingSchedule == null) {
            throw new EntityNotFoundException("Schedule not found");
        }

        LocalDateTime startTime = scheduleDTO.getStartDateTime();
        LocalDateTime endTime = startTime.plusHours(1);
        TennisCourtDTO tennisCourtDTO = scheduleDTO.getTennisCourt();

        Schedule existingTimeSlot = scheduleRepository.findExistingSchedule(tennisCourtDTO.getId(), startTime, endTime);

        if (existingTimeSlot != null) {
            throw new BusinessException("Time conflict exists for chosen schedule slot.");
        }

        existingSchedule.setEndDateTime(scheduleDTO.getEndDateTime());
        existingSchedule.setStartDateTime(scheduleDTO.getStartDateTime());
        existingSchedule.setTennisCourt(tennisCourtMapper.map(tennisCourtDTO));
        scheduleRepository.saveAndFlush(existingSchedule);

        return existingSchedule;
    }

    public List<ScheduleDTO> findAllSchedules() {
        return scheduleMapper.map(scheduleRepository.findAll());
    }

    public void removeSchedule(Long scheduleId) {
        Schedule existingSchedule = scheduleRepository.findFirstById(scheduleId);

        if (existingSchedule == null) {
            throw new EntityNotFoundException("Schedule not found");
        }

        scheduleRepository.delete(existingSchedule);
    }

    public List<Schedule> findAllFreeScheduleSlots() {
        Date timeNow = new Date();
        return scheduleRepository.findAllFutureScheduleSlots(timeNow);
    }
}
