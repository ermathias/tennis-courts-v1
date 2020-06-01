package com.tenniscourts.guests;

import java.util.Collection;

import com.tenniscourts.guests.to.GuestCreateDTO;
import com.tenniscourts.guests.to.GuestDTO;
import com.tenniscourts.guests.to.GuestUpdateDTO;

public interface GuestService {

	Collection<GuestDTO> findAll();

	GuestDTO find(
		long id);

	Collection<GuestDTO> findByName(
		String name);

	GuestDTO save(
		GuestCreateDTO dto);

	GuestDTO update(
		Long id,
		GuestUpdateDTO dto);

	boolean delete(
		long id);


}
