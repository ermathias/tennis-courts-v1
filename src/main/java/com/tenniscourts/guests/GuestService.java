package com.tenniscourts.guests;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    public GuestDTO addGuest(GuestDTO guest) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guest)));
    }

    public GuestDTO findGuestById(Long guestId) {
        return guestMapper.map(guestRepository.findById(guestId).orElseThrow(() -> new EntityNotFoundException("Guest was not found.")));
    }

    public List<GuestDTO> findAllGuests() {
        return guestRepository.findAll().stream().map(guestMapper::map).collect(Collectors.toList());
    }

    public List<GuestDTO> findGuestByName(String name) {
        return guestRepository.findByName(name).stream().map(guestMapper::map).collect(Collectors.toList());
    }

    public GuestDTO updateGuest(GuestDTO guestDTO) {
        return guestMapper.map(guestRepository.findById(guestDTO.getId())
                                              .map(guest -> {
                                                guest.setName(guestDTO.getName());
                                                    return guestRepository.save(guest);
                                                })
                                              .orElseThrow(() -> new EntityNotFoundException("Guest was not found.")));
    }

    public void deleteGuestById(Long guestId) {
         Guest guest = guestRepository.findById(guestId)
                        .orElseThrow(() -> new EntityNotFoundException("Guest was not found."));

         guestRepository.delete(guest);
    }
}
