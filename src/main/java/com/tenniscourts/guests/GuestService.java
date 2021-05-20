package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    public GuestDTO addGuest(GuestDTO guestDto) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guestDto)));
    }

    public GuestDTO findGuestById(Long id) {
        return guestRepository.findById(id).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }

    public GuestDTO findGuestByName(String name) {
        return guestRepository.findByName(name).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }

    public List<GuestDTO> findAllGuests() {
        return guestRepository.findAll().stream().map(guestMapper::map).collect(Collectors.toList());
    }

    public GuestDTO deleteGuest(Long id){
        GuestDTO g =  guestRepository.findById(id).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
        guestRepository.deleteById(id);
        return g;
    }

    public GuestDTO updateGuest(GuestDTO guestDTO) {
        Guest g = guestRepository.findById(guestDTO.getId()).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });

        g.setName(guestDTO.getName());
        guestRepository.save(g);
        return guestRepository.findById(guestDTO.getId()).map(guestMapper::map).get();
    }

}
