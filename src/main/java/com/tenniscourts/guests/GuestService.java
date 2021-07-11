package com.tenniscourts.guests;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GuestService {

	private final GuestRepository guestRepository;

	private ModelMapper modelmapper = new ModelMapper();

	public GuestDTO createGuest(GuestDTO guestDTO) {

		Guest guest = guestRepository.save(modelmapper.map(guestDTO, Guest.class));

		return modelmapper.map(guest, GuestDTO.class);

	}

	public List<GuestDTO> findGuests() {
		return guestRepository.findAll().stream().map(guest -> modelmapper.map(guest, GuestDTO.class))
				.collect(Collectors.toList());
	}

}
