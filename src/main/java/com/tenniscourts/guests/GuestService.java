package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GuestService {

    private GuestRepository guestRepository;
    private GuestMapper guestMapper;

    public GuestDTO addGuest(GuestDTO guest) {
        guest.setId(null);
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guest)));
    }

    public GuestDTO findGuestById(Long id){
        return guestRepository.findById(id).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not Found");
        });
    }

    public List<GuestDTO> findAllGuests(){
        return guestMapper.map(guestRepository.findAll());
    }

    public void deleteGuest(Long id){
        findGuestById(id);
        guestRepository.deleteById(id);
    }

    public void updateGuest(GuestDTO guest){
        guestRepository.saveAndFlush(guestMapper.map(guest));
    }

    public GuestDTO findGuestByName(String guest){
        GuestDTO obj = guestMapper.map(guestRepository.findByName(guest));

        if(obj == null){
            throw new EntityNotFoundException("Guest named '" +guest.toUpperCase()+"' doesn't exist");
        }
        return obj;
    }

}
