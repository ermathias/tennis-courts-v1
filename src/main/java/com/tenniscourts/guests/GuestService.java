package com.tenniscourts.guests;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class GuestService {

    @Autowired
    private final GuestMapper guestMapper;

    @Autowired
    private final GuestRepository guestRepository;

    public List<GuestDTO> findAll(Pageable pageable) {
        return guestMapper.map(guestRepository.findAll(pageable).getContent());
    }

    public GuestDTO findById(Long id) {
        return guestMapper.map(guestRepository.findById(id).get());
    }

    public List<GuestDTO> findByName(String name) {
        return guestMapper.map(guestRepository.findByName(name));
    }

    @Transactional(rollbackFor = Exception.class)
    public GuestDTO insert(GuestDTO dto) {
        return guestMapper.map(guestRepository.save(guestMapper.map(dto)));
    }

    public void delete(Long id) {
        guestRepository.deleteById(id);
    }

}
