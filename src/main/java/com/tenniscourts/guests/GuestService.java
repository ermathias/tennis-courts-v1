package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    @Autowired
    public GuestService(GuestRepository guestRepository, GuestMapper guestMapper) {
        this.guestRepository = guestRepository;
        this.guestMapper = guestMapper;
    }

    public GuestDTO addGuest(GuestDTO guestDTO) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guestDTO)));
    }

    public GuestDTO findGuestById(Long id) {
        return guestRepository.findById(id)
                .map(guestMapper::map)
                .orElseThrow(() -> throwEntityNotFoundException(id));
    }

    public GuestDTO findGuestByName(String name) {
        return guestRepository.findByName(name)
                .map(guestMapper::map)
                .orElseThrow(() -> throwEntityNotFoundException(name));
    }

    public GuestDTO updateGuest(GuestDTO guestDTO, Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> throwEntityNotFoundException(id));
        guest.setName(guestDTO.getName());
        return guestMapper.map(guestRepository.save(guest));
    }

    public GuestDTO removeGuest(Long id) {
        return guestMapper.map(this.remove(id));
    }

    private Guest remove(Long id) {
        return guestRepository.findById(id)
                .map(guest -> {
                    guestRepository.delete(guest);
                    return guest;
                }).orElseThrow(() -> throwEntityNotFoundException(id));
    }

    public List<GuestDTO> findAll() {
        return guestRepository.findAll().stream()
                .map(guestMapper::map)
                .collect(toList());
    }

    private EntityNotFoundException throwEntityNotFoundException(Object guest) {
        return new EntityNotFoundException(String.format("Guest %s not found.", guest));
    }

}
