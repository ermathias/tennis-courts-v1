package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    public GuestDTO addGuest(GuestDTO guest) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guest)));
    }

    public GuestDTO findGuestById(Long id) {
        return guestMapper.map(findGuestEntityById(id));
    }

    public Guest findGuestEntityById(Long id) {
        return guestRepository.findById(id).orElseThrow(guestNotFound());
    }

    public List<GuestDTO> findGuestByName(String name) {
        return guestRepository.findByName(name).stream().map(guestMapper::map).collect(Collectors.toList());
    }

    public List<GuestDTO> findAllGuests() {
        return guestRepository.findAll().stream().map(guestMapper::map).collect(Collectors.toList());
    }

    public Long deleteGuestById(Long id) {
        return guestRepository.findById(id).map(guest -> {
            guestRepository.deleteById(guest.getId());
            return guest.getId();
        }).orElseThrow(guestNotFound());
    }

    public GuestDTO updateGuest(GuestDTO guestDTO) {
        return guestMapper.map(update(guestDTO));
    }

    private Guest update(GuestDTO guestDTO) {
        return guestRepository.findById(guestDTO.getId()).map(guest -> {
            guest.setName(guestDTO.getName());
            return guestRepository.save(guest);
        }).orElseThrow(guestNotFound());
    }

    private Supplier<RuntimeException> guestNotFound() {
        return () -> {
            throw new EntityNotFoundException("Guest not found.");
        };
    }
}