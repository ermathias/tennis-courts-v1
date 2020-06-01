package com.tenniscourts.guests;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.to.GuestCreateDTO;
import com.tenniscourts.guests.to.GuestDTO;
import com.tenniscourts.guests.to.GuestUpdateDTO;

//TODO - add UT coverage
//TODO - add more testable design by inheritance or componentization
//@Service - FIXME
@Component
public final class GuestServiceDefault
	implements
	GuestService {

	private final GuestRepository repository;
	private final GuestMapper mapper;

	@Autowired
	public GuestServiceDefault(
		final GuestRepository repository,
		final GuestMapper mapper) {
		super();
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public Collection<GuestDTO> findAll() {
		Collection<Guest> guests = repository.findAll();
		return mapper.fromEntity(guests);
	}

	@Override
	public GuestDTO find(
		final long id) {
		Guest guest = findById(id);
		return mapper.fromEntity(guest);
	}

	@Override
	public Collection<GuestDTO> findByName(
		final String name) {
		Collection<Guest> guests = repository.findByName(name);
		return mapper.fromEntity(guests);
	}

	@Override
	public GuestDTO save(
		final GuestCreateDTO dto) {
		Guest entityunsaved = mapper.toEntity(dto);
		Guest entitySaved = repository.save(entityunsaved);
		return mapper.fromEntity(entitySaved);
	}

	@Override
	public GuestDTO update(
		final Long id,
		final GuestUpdateDTO dto) {
		Guest entityReference = findById(id);
		Guest entityUnsaved = mapper.updateEntity(entityReference, dto);
		Guest entitySaved = repository.save(entityUnsaved);
		return mapper.fromEntity(entitySaved);
	}

	@Override
	public boolean delete(
		final long id) {
		repository.deleteById(id);
		return true;
	}

	private Guest findById(
		final long id) {
		return repository
				.findById(id)
				.orElseThrow(() -> {
					throw new EntityNotFoundException("Guest not found.");
				});
	}

}
