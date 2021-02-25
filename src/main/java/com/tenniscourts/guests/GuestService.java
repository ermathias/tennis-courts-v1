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
    public GuestDTO insert(GuestDTO guestDTO) {
        Guest newGuest = Guest.builder().id(guestDTO.getId()).name(guestDTO.getName()).build();
        return guestMapper.map(guestRepository.save(newGuest));
    }

    @Transactional
    public GuestDTO update(GuestDTO guestDTO) {
        Guest savedGuest = null;

        savedGuest = guestRepository.findById(guestDTO.getId()).orElseThrow(() -> {
            throw new EntityNotFoundException(createGuestNotFoundMessage(guestDTO.getId()));
        });

        savedGuest.setName(guestDTO.getName());

        return guestMapper.map(guestRepository.save(savedGuest));
    }

    public void delete(Long id) {
        Guest savedGuest = guestRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(createGuestNotFoundMessage(id));
        });

        guestRepository.delete(savedGuest);
    }

    public GuestDTO findGuest(Long id) {
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

    public Guest findById(Long id) {
        return guestRepository.findById(id).orElse(null);
    }
}