package com.tenniscourts.tenniscourts;

import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TennisCourtService {

    @Inject
    private final TennisCourtRepository tennisCourtRepository;

    @Inject
    private final ScheduleService scheduleService;

    @Inject
    private final TennisCourtMapper tennisCourtMapper;

    @Transactional
    public TennisCourtDTO addTennisCourt(TennisCourtDTO tennisCourt) {
        return tennisCourtMapper.map(tennisCourtRepository.save(tennisCourtMapper.map(tennisCourt)));
    }

    public TennisCourtDTO findTennisCourtById(Long id) {
        return findById(id).map(tennisCourtMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException(getNotFoundMessage());
        });
    }

    public TennisCourtDTO findTennisCourtWithSchedulesById(Long tennisCourtId) {
        TennisCourt savedTennisCourt = findById(tennisCourtId).orElse(null);

        if (savedTennisCourt == null) {
            throw new EntityNotFoundException(getNotFoundMessage());
        }

        TennisCourtDTO tennisCourtDTO = tennisCourtMapper.map(savedTennisCourt);
        tennisCourtDTO.setTennisCourtSchedules(scheduleService.findSchedulesByTennisCourt(savedTennisCourt));
        return tennisCourtDTO;
    }

    public Optional<TennisCourt> findById(Long id) {
        return tennisCourtRepository.findById(id);
    }

    private String getNotFoundMessage() {
        return "Tennis Court not found.";
    }
}
