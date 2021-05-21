package com.tenniscourts.tenniscourts;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TennisCourtService {

    private final TennisCourtRepository tennisCourtRepository;
    private final ScheduleService scheduleService;
    private final TennisCourtMapper tennisCourtMapper;

    public TennisCourtDTO addTennisCourt(CreateTennisCourtRequestDTO tennisCourt) {
        return tennisCourtMapper.map(tennisCourtRepository.saveAndFlush(tennisCourtMapper.map(tennisCourt)));
    }

    public TennisCourtDTO findTennisCourtById(Long id, boolean includeSchedules) {
        TennisCourtDTO tennisCourtDTO = tennisCourtRepository.findById(id).map(tennisCourtMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Tennis Court not found."));
        if (includeSchedules)
            tennisCourtDTO.setTennisCourtSchedules(scheduleService.findSchedulesByTennisCourtId(id));
        return tennisCourtDTO;
    }
}
