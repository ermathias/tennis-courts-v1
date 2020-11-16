package com.tenniscourts.tenniscourts;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    public TennisCourtDTO addTennisCourt(TennisCourtDTO dto) {
        return tennisCourtMapper.map(tennisCourtRepository.saveAndFlush(tennisCourtMapper.map(dto)));
    }

    public TennisCourtDTO findTennisCourtById(Long id) {
        return tennisCourtRepository.findById(id).map(tennisCourtMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Tennis Court not found.");
        });
    }

    public List<TennisCourtDTO> findAll(Pageable pageable) {
        return tennisCourtMapper.map(tennisCourtRepository.findAll(pageable).getContent());
    }

    public List<TennisCourtDTO> findTennisCourtByName(String name) {
        return tennisCourtMapper.map(tennisCourtRepository.findByName(name));
    }

    public TennisCourtDTO findTennisCourtWithSchedulesById(Long id) {
        TennisCourtDTO dto = findTennisCourtById(id);
        dto.setTennisCourtSchedules(scheduleService.findSchedulesByTennisCourtId(id));

        return dto;
    }

}
