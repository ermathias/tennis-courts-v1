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


    public TennisCourtDTO addTennisCourt(TennisCourtDTO tennisCourtDTO) {
        return tennisCourtMapper.map(tennisCourtRepository.saveAndFlush(tennisCourtMapper.map(tennisCourtDTO)));
    }

    public TennisCourtDTO findTennisCourtById(Long id) {
        return tennisCourtRepository.findById(id).map(tennisCourtMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Tennis Court not found.");
        });
    }

    public List<TennisCourtDTO> findTennisCourtByName(String name) {
        List<TennisCourt> tennisCourts = tennisCourtRepository.findByName(name);
        List<TennisCourtDTO> tennisCourtDTOList = new ArrayList();

        if(tennisCourts.isEmpty()){
            throw new EntityNotFoundException("Tennis Court not found.");
        }

        for (TennisCourt tennisCourt: tennisCourts)
        {
            TennisCourtDTO tennisCourtDTO = tennisCourtMapper.map(tennisCourt);
            tennisCourtDTOList.add(tennisCourtDTO);
        }
        return tennisCourtDTOList ;

    }

    public TennisCourtDTO findTennisCourtWithSchedulesById(Long id) {
        TennisCourtDTO tennisCourtDTO = findTennisCourtById(id);
        tennisCourtDTO.setTennisCourtSchedules(scheduleService.findSchedulesByTennisCourtId(id));
        return tennisCourtDTO;
    }


    public void deleteTennisCourtById (Long scheduleId, Long tennisCourtId) {
        scheduleService.deleteScheduleById(scheduleId);
        tennisCourtRepository.deleteById(tennisCourtId);
    }


}
