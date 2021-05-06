package com.tenniscourts.tenniscourts;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.ScheduleDTO;
import com.tenniscourts.schedules.ScheduleService;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TennisCourtService {

	@Autowired
    TennisCourtRepository tennisCourtRepository;

	@Autowired
    ScheduleService scheduleService;

	@Autowired
    TennisCourtMapper tennisCourtMapper;

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
    
    public TennisCourtDTO findTennisCourtByname(String name) {
        return tennisCourtRepository.findByname(name).map(tennisCourtMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Tennis Court not found by name.");
        });
    }
    
    public TennisCourtDTO updateTennisCourt(TennisCourtDTO tennisCourt) {
        return tennisCourtMapper.map(tennisCourtRepository.save(tennisCourtMapper.map(tennisCourt)));
    }
    
    public Map<string,Boolean> deleteTennisCourt(Long tennisCourtId) {
    	TennisCourtDTO court = tennisCourtRepository.findById(id).map(tennisCourtMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Tennis Court not found.");
    });
            tennisCourtRepository.delete(court);  
            Map<string,Boolean> response =new Hasmap<>();
            response.put("deleted",Boolean.TRUE);
        return response;
    }
    
}
