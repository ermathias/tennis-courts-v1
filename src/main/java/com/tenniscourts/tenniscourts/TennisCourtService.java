package com.tenniscourts.tenniscourts;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TennisCourtService {

    @Autowired
    private final TennisCourtRepository tennisCourtRepository;

    @Autowired
    private final ScheduleService scheduleService;

    @Autowired
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
        TennisCourtDTO tennisCourtDTO = tennisCourtMapper.map(tennisCourtRepository.findFirstById(tennisCourtId));

        if (tennisCourtDTO == null) {
            throw new EntityNotFoundException("Tennis Court not found.");
        }

        tennisCourtDTO.setTennisCourtSchedules(scheduleService.findSchedulesByTennisCourtId(tennisCourtId));

        return tennisCourtDTO;
    }

    public List<TennisCourt> listAllTennisCourts() {
        return tennisCourtRepository.findAll();
    }

    public void removeTennisCourt(Long tennisCourtId) {
        TennisCourt existingTennisCourt = tennisCourtRepository.findFirstById(tennisCourtId);

        if (existingTennisCourt == null) {
            throw new EntityNotFoundException("Tennis Court not found.");
        }

        tennisCourtRepository.delete(existingTennisCourt);
    }
}
