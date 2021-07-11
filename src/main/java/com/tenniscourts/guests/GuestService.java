package com.tenniscourts.guests;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GuestService {

	private final GuestRepository guestRepository;

	private final GuestMapper guestMapper;

	public GuestDTO createGuest(CreateGuestRequestDTO createGuestRequestDTO) {
		throw new UnsupportedOperationException();
	}

	public List<GuestDTO> findGuests() {
		return guestRepository.findAll().stream().map(guestMapper::map).collect(Collectors.toList());
	}

}
