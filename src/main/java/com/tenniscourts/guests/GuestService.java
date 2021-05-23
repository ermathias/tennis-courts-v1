package com.tenniscourts.guests;

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

    public List<GuestDTO> getAllGuests() {
        return guestMapper.map(guestRepository.findAll());
    }

    public List<GuestDTO> searchGuestByName(String name){
        return guestMapper.map(guestRepository.searchGuestByName(name));
    }

    @Transactional(rollbackFor = Exception.class)
    public GuestDTO saveGuest(GuestDTO guestDTO){
        return guestMapper.map(guestRepository.save(guestMapper.map(guestDTO)));
    }
}
