package com.tenniscourts.guests;



import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;


    public GuestDTO addGuest(Long userId, GuestDTO guest){
        Guest userAction = guestRepository.getOne(userId);
        if(userAction.isAdmin()){
            return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guest)));
        }
        throw new UnsupportedOperationException("Only admin users are allowed to do that.");
    }

    public GuestDTO updateGuest(Long userId, GuestDTO guest){
        Guest userAction = guestRepository.getOne(userId);
        if(userAction.isAdmin()){
            return guestMapper.map(guestRepository.save(guestMapper.map(guest)));
        }
        throw new UnsupportedOperationException("Only admin users are allowed to do that.");
    }

    public GuestDTO findGuestById(Long id){
        Guest temp = guestRepository.getOne(id);
        return guestMapper.map(temp);
    }

    public List<GuestDTO> findAllGuests(Long userId){

        Guest userAction = guestRepository.getOne(userId);
        if(userAction.isAdmin()){
            List<Guest> guests = guestRepository.findAll();
            List<GuestDTO> guestsDTO = guestMapper.map(guests);
            return guestsDTO;
        }
        throw new UnsupportedOperationException("Only admin users are allowed to do that.");
    }

    public List<GuestDTO> findGuestsByName(Long userId, String name){

        Guest userAction = guestRepository.getOne(userId);
        if(userAction.isAdmin()){
            List<Guest> guests = guestRepository.findByName(name);
            List<GuestDTO> guestsDTO = guestMapper.map(guests);
            return guestsDTO;
        }
        throw new UnsupportedOperationException("Only admin users are allowed to do that.");
    }

    public void deleteGuest(Long userId, Long id){
        Guest userAction = guestRepository.getOne(userId);
        Guest guest = guestRepository.getOne(id);

        if(userAction.isAdmin()){
            guestRepository.delete(guest);
        }else{
            throw new UnsupportedOperationException("Only admin users are allowed to delete guests.");
        }
    }
}
