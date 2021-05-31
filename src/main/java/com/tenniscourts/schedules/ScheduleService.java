package com.tenniscourts.schedules;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        //TODO: implement addSchedule
        ScheduleDTO scheduleDto = new ScheduleDTO();
        scheduleDto.setTennisCourtId(tennisCourtId);
        scheduleDto.setStartDateTime(createScheduleRequestDTO.getStartDateTime());
        return scheduleMapper.map(scheduleRepository.saveAndFlush(scheduleMapper.map(scheduleDto)));
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        //TODO: implement
        return null;
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        //TODO: implement
        return null;
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }

    public Schedule findScheduleId(Long scheduleId) {
        return scheduleRepository.findScheduleById(scheduleId);
    }
}
