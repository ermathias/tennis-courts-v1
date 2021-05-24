package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
public class GuestService {

    @Autowired
    private final GuestRepository guestRepository;

    @Autowired
    private final GuestMapper guestMapper;

    public List<GuestDTO> getAll() {
        return guestMapper.map(guestRepository.findAll());
    }

    public List<GuestDTO> findByNameContaining(String name){
        return guestMapper.map(guestRepository.findByNameContaining(name));
    }

    public GuestDTO findById(Long guestId) throws EntityNotFoundException {
        if(!guestRepository.findById(guestId).isPresent()){
            throw new EntityNotFoundException("Guest not found");
        }
        return guestMapper.map(guestRepository.findById(guestId).get());
    }

    @Transactional(rollbackFor = Exception.class)
    public GuestDTO save(GuestDTO guestDTO) throws EntityNotFoundException {
        if(!guestDTO.getId().equals(null)){
            if(!guestRepository.findById(guestDTO.getId()).isPresent()){
                throw new EntityNotFoundException("Couldn't update guest\nCaused by: Guest not found");
            }
        }
        return guestMapper.map(guestRepository.save(guestMapper.map(guestDTO)));
    }

    public void delete(Long guestId){
        guestRepository.deleteById(guestId);
    }
}
