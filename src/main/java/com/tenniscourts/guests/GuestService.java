package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;
    private static final String NAME_CANT_BE_BLANK = "Guest name can't be blank";
    private static final String ID_CANT_BE_NULL = "Guest id can't be null";

    public GuestDTO createGuest(CreateGuestDTO createGuestDTO) {
        Validate.notBlank(createGuestDTO.getName(), NAME_CANT_BE_BLANK);
        return guestMapper.map(guestRepository.save(Guest.builder().name(createGuestDTO.getName()).build()));
    }

    public Page<GuestDTO> getGuestsPaged(Integer page, Integer pageSize) {
        Validate.notNull(page, "Page can't be null");
        Validate.notNull(pageSize, "Page size can't be null");

        Pageable pageable = PageRequest.of(page, pageSize);
        return guestRepository.findAll(pageable).map(guestMapper::map);
    }

    public List<GuestDTO> listAllGuests() {
        return guestRepository.findAll().stream().map(guestMapper::map).collect(Collectors.toList());
    }

    public GuestDTO findGuestById(Long guestId) {
        Guest guest = findGuestByIdOrThrow(guestId);
        return guestMapper.map(guest);
    }

    public List<GuestDTO> findGuestsByName(String guestName) {
        Validate.notBlank(guestName, NAME_CANT_BE_BLANK);
        return guestMapper.map(guestRepository.findAllByNameContainingIgnoreCase(guestName));
    }

    public GuestDTO updateGuest(Long guestId, CreateGuestDTO createGuestDTO) {
        Validate.notBlank(createGuestDTO.getName(), NAME_CANT_BE_BLANK);
        Guest guest = findGuestByIdOrThrow(guestId);
        guest.setName(createGuestDTO.getName());
        return guestMapper.map(guestRepository.save(guest));
    }

    public GuestDTO deleteGuest(Long guestId) {
        Guest guest = findGuestByIdOrThrow(guestId);
        guestRepository.delete(guest);
        return guestMapper.map(guest);
    }

    private Guest findGuestByIdOrThrow(Long guestId) {
        Validate.notNull(guestId, ID_CANT_BE_NULL);
        return guestRepository.findById(guestId).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }
}
