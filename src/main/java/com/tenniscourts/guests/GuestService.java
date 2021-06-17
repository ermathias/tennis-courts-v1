package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    public GuestDTO findGuestById(Long id){
        return guestRepository.findById(id).map(guestMapper::map).orElseThrow(
                () -> new EntityNotFoundException("Guest by id " + id + " was not founded!"));
    }

    public  List<GuestDTO> findGuestByName(String name) {
        return guestMapper.map(guestRepository.findByName(name));
    }

    public List<GuestDTO> findAllGuests() {
        return guestRepository.findAll().stream().map(guestMapper::map).collect(Collectors.toList());
    }

    public GuestDTO addGuest(CreateGuestDTO guest) {
        return guestMapper.map(guestRepository.save(Guest.builder().name(guest.getName()).build()));
    }

    public GuestDTO updateGuest(GuestDTO guest) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guest)));
    }

    public GuestDTO deleteGuest(Long id) {
        Guest guest = guestRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Guest by id " + id + " was not founded!"));
        guestRepository.delete(guest);
        return guestMapper.map(guest);
    }
}
