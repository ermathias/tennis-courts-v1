package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GuestService {


    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    // Create and update both functionality works with addUser
    public GuestUserDTO addUpdateUser(GuestUserDTO guestUser) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guestUser)));
    }


    public GuestUserDTO deleteUser(Long userID) {
        GuestUserDTO gdto =  guestRepository.findById(userID).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("User already deleted / not exist.");
        });
        guestRepository.deleteById(userID);
        return gdto;
    }

    public GuestUserDTO findUser(Long id) {
       return  guestRepository.findById(id).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("User Not found.");
        });
    }

    public List<Guest> findAllGuests (){
        return guestRepository.findAll();
    }


    public List<GuestUserDTO> findUserByName(String name) {
        return guestMapper.map(guestRepository.findByName(name));
    }
}
