package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class KiranjyothiPRService {

    private final KiranjyothiPRRepository guestRepository;

    private final KiranjyothiPRMapper guestMapper;

    public KiranjyothiPRDTO addGuest(KiranjyothiPRDTO guest) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guest)));
    }

    public KiranjyothiPRDTO update(KiranjyothiPRDTO guestDTO) {

        Guest guest = guestRepository.findById(guestDTO.getId()).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });

        guest.setName(guestDTO.getName());

        return guestMapper.map(guestRepository.saveAndFlush(guest));
    }

    public KiranjyothiPRDTO getById(long id) {
        return guestRepository.findById(id).map(guestMapper::map).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }

    public List<KiranjyothiPRDTO> getAll() {
        return guestRepository.findAll().stream().map(guestMapper::map).collect(Collectors.toList());
    }

    public KiranjyothiPRDTO getByName(String name) {
        return guestRepository.findByName(name).map(guestMapper::map).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }

    public KiranjyothiPRDTO deleteById(long id) {
        Guest guest = guestRepository.findById(id).<EntityNotFoundException>orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });

        guestRepository.delete(guest);

        return guestMapper.map(guest);
    }


}
