package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.BusinessException;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    private final TennisCourtService tennisCourtService;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper,
                           TennisCourtService tennisCourtService) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
        this.tennisCourtService = tennisCourtService;
    }

    public ScheduleDTO addSchedule(CreateScheduleRequestDTO createScheduleRequestDTO) {
        ScheduleDTO scheduleDTO = createScheduleDTO(createScheduleRequestDTO);

        checkAddSchedule(scheduleDTO);

        return scheduleMapper.map(scheduleRepository.saveAndFlush(scheduleMapper.map(scheduleDTO)));
    }

    private ScheduleDTO createScheduleDTO(CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ScheduleDTO.builder()
                .startDateTime(createScheduleRequestDTO.getStartDateTime())
                .endDateTime(createScheduleRequestDTO.getEndDateTime())
                .tennisCourtId(createScheduleRequestDTO.getTennisCourtId())
                .tennisCourt(tennisCourtService.findTennisCourtById(createScheduleRequestDTO.getTennisCourtId()))
                .build();
    }

    public ScheduleDTO findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .map(scheduleMapper::map)
                .orElseThrow(() -> throwEntityNotFoundException(scheduleId));
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long courtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourtIdOrderByStartDateTime(courtId));
    }

    private EntityNotFoundException throwEntityNotFoundException(Object schedule) {
        return new EntityNotFoundException(String.format("Schedule %s not found.", schedule));
    }

    private void checkAddSchedule(ScheduleDTO scheduleDTO) {
        LocalDateTime startDateTime = scheduleDTO.getStartDateTime();
        LocalDateTime endDateTime = scheduleDTO.getEndDateTime();

        if (startDateTime.isBefore(LocalDateTime.now())) {
            throwBusinessException("The schedule cannot be before from local date time.");
        }
        if (endDateTime.isBefore(startDateTime)) {
            throwBusinessException("The schedule cannot be before from start date time.");
        }
    }

    private void throwBusinessException(String message) {
        throw new BusinessException(message);
    }

}
