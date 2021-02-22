package com.tenniscourts.guests;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.tenniscourts.exceptions.EntityNotFoundException;

@Service
@AllArgsConstructor
public class GuestService {
    @Inject
    private final GuestRepository guestRepository;

    @Inject
    private final GuestMapper guestMapper;

    @Transactional
    public GuestDTO save(GuestDTO guestDTO) {
        Guest guest = Guest.builder().id(guestDTO.getId()).name(guestDTO.getName()).build();
        return guestMapper.map(guestRepository.save(guest));
    }

    public void delete(Long id) {
        Guest savedGuest = guestRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(createGuestNotFoundMessage(id));
        });

        guestRepository.delete(savedGuest);
    }

    public GuestDTO findById(Long id) {
        return guestRepository.findById(id).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException(createGuestNotFoundMessage(id));
        });
    }

    private String createGuestNotFoundMessage(Long id) {
        return "Guest not found: ".concat(id.toString());
    }

    public List<GuestDTO> findByName(String name, Pageable pageable) {
        return guestMapper.map(guestRepository.findByName(name, pageable).getContent());
    }

    public List<GuestDTO> findAll(Pageable pageable) {
        return guestMapper.map(guestRepository.findAll(pageable).getContent());
    }
}