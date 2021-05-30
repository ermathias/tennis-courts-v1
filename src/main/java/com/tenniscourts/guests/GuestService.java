package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GuestService {

    public final GuestRepository guestRepository;
    public final GuestMapper guestMapper;

    public GuestDTO findGuestById(Long id) {
        return guestRepository.findById(id).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }

    public List<GuestDTO> findGuestByName(String name) {
        List<Guest> guestsList = guestRepository.findByName(name);
        List<GuestDTO> guestDTOList = new ArrayList();
        if(guestsList.isEmpty()){
            throw new EntityNotFoundException("Guest not found.");
        }
        for (Guest guest: guestsList)
        {
            GuestDTO guestDTO = guestMapper.map(guest);
            guestDTOList.add(guestDTO);
        }
        return guestDTOList;

    }

    public GuestDTO addGuest(GuestDTO guestDTO) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guestDTO)));
    }

    public List<Guest> listAll(){
        return guestRepository.findAll();
    }

    public Long updateGuest(Long id, Guest guest) {
        GuestDTO guestById = findGuestById(id);
        guestById.setName(guest.getName());
        guestMapper.map(guestRepository.save(guestMapper.map(guestById)));
        return id;
    }

    public void deleteGuestById (Long guestId) {
        guestRepository.deleteById(guestId);
    }
}
