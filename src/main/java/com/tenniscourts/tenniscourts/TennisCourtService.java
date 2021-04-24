package com.tenniscourts.tenniscourts;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TennisCourtService {

    private final TennisCourtRepository tennisCourtRepository;

    private final ScheduleService scheduleService;

    private final TennisCourtMapper tennisCourtMapper;

    public TennisCourtDTO addTennisCourt(TennisCourtDTO tennisCourt) {
        return tennisCourtMapper.map(tennisCourtRepository.saveAndFlush(tennisCourtMapper.map(tennisCourt)));
    }

    public TennisCourtDTO findTennisCourtById(Long id) {
        return tennisCourtRepository.findById(id).map(tennisCourtMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Tennis Court not found.");
        });
    }

    public TennisCourtDTO findTennisCourtWithSchedulesById(Long tennisCourtId) {
        TennisCourtDTO tennisCourtDTO = findTennisCourtById(tennisCourtId);
        tennisCourtDTO.setTennisCourtSchedules(scheduleService.findSchedulesByTennisCourtId(tennisCourtId));
        return tennisCourtDTO;
    }

    public List<TennisCourtDTO> findFreeSchedulesByTennisCourtId() {
        var tennisCourts = tennisCourtRepository.findAll();
        var tennisCourtDTOS = new ArrayList<TennisCourtDTO>();

        for (TennisCourt tennisCourt : tennisCourts) {
            var schedulesDTO = scheduleService.findFreeSchedulesByTennisCourtId(tennisCourt.getId());
            var tennisCourtDTO = TennisCourtDTO
                    .builder()
                    .id(tennisCourt.getId())
                    .name(tennisCourt.getName())
                    .tennisCourtSchedules(schedulesDTO)
                    .build();
            tennisCourtDTOS.add(tennisCourtDTO);
        }
        return tennisCourtDTOS;
    }

}
