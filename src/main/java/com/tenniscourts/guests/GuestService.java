package com.tenniscourts.guests;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.EntityNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GuestService {

	private final GuestRepository guestRepository;

	private ModelMapper mapper = new ModelMapper();

	public GuestDTO createGuest(GuestDTO guestDTO) {

		Guest guest = guestRepository.save(mapper.map(guestDTO, Guest.class));

		return mapper.map(guest, GuestDTO.class);

	}

	public List<GuestDTO> retrieveGuests() {

		return guestRepository.findAll().stream().map(guest -> mapper.map(guest, GuestDTO.class))
				.collect(Collectors.toList());

	}

	public GuestDTO retrieveGuestById(Long guestId) {

		return guestRepository.findById(guestId).map(guest -> {

			return mapper.map(guest, GuestDTO.class);

		}).orElseThrow(() -> {
			throw new EntityNotFoundException("Guest not found.");
		});

	}

	public GuestDTO retrieveGuestByName(GuestDTO guestName) {

		return guestRepository.findByName(guestName.getName()).map(guest -> {

			return mapper.map(guest, GuestDTO.class);

		}).orElseThrow(() -> {
			throw new EntityNotFoundException("Guest not found.");
		});

	}

}
