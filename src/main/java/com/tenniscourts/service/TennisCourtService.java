package com.tenniscourts.service;

import com.tenniscourts.dto.ScheduleDTO;
import com.tenniscourts.dto.TennisCourtDTO;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.mapper.TennisCourtMapper;
import com.tenniscourts.model.TennisCourt;
import com.tenniscourts.repository.TennisCourtRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return tennisCourtRepository.findById(id).map(tennisCourtMapper::map).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Tennis Court not found.");
        });
    }

    public TennisCourtDTO findTennisCourtWithSchedulesById(Long tennisCourtId) {
        TennisCourtDTO tennisCourtDTO = findTennisCourtById(tennisCourtId);
        tennisCourtDTO.setTennisCourtSchedules(scheduleService.findSchedulesByTennisCourtId(tennisCourtId));
        return tennisCourtDTO;
    }

    public List<TennisCourtDTO> findAllTennisCourt() {
        return tennisCourtMapper.map(tennisCourtRepository.findAll());
    }

    public List<ScheduleDTO> findFreeSlots() {

        List<TennisCourtDTO> allTennisCourt = findAllTennisCourt();
        for (TennisCourtDTO tennisCourtDto : allTennisCourt){
            tennisCourtDto.getTennisCourtSchedules();
        }
        //TODO: implement free slots;
        return null;

    }

}
