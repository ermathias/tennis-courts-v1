package com.tenniscourts.guests;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@AllArgsConstructor
public class GuestService {

    @Autowired
    private final GuestRepository guestRepository;

    @Autowired
    private final GuestMapper guestMapper;

    @Transactional(rollbackFor = Exception.class)
    public GuestDTO addGuest(GuestDTO guest) {
        return guestMapper.map(guestRepository.save(guestMapper.map(guest)));
    }

    public GuestDTO findGuestById(Long guestId) {
        return guestMapper.map(guestRepository.findById(guestId).get());
    }

    public List<GuestDTO> findGuestByName(String guestName) {
        return guestMapper.map(guestRepository.findByName(guestName));
    }

    public List<GuestDTO> findAll(Pageable pageable) {
        return guestMapper.map(guestRepository.findAll(pageable).getContent());
    }

    public void deleteById(Long id) {
        guestRepository.deleteById(id);
    }
}
