package com.tenniscourts.guests;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * Guest interface implementation
 */
@Service
@RequiredArgsConstructor
@Transactional
public class GuestServiceImpl implements GuestService {

	private final GuestRepository guestRepository;

	@Override
	public GuestDTO addGuest(GuestRequest guestRequest) {
		return GuestMapper.GUEST_MAPPER_INSTANCE
				.map(guestRepository.saveAndFlush(GuestMapper.GUEST_MAPPER_INSTANCE.map(guestRequest)));
	}

	@Override
	public GuestDTO modifyGuest(GuestDTO guestDTO) {
		Guest guest = GuestMapper.GUEST_MAPPER_INSTANCE.map(findGuestById(guestDTO.getId()));
		guest.setName(guestDTO.getName());
		return GuestMapper.GUEST_MAPPER_INSTANCE.map(guestRepository.saveAndFlush(guest));
	}

	@Override
	public GuestDTO findGuestById(Long guestId) throws EntityNotFoundException {
		return guestRepository.findById(guestId).map(GuestMapper.GUEST_MAPPER_INSTANCE::map)
				.orElseThrow(() -> new EntityNotFoundException("Guest not found"));
	}

	@Override
	public List<GuestDTO> findGuestByName(String name) {
		return guestRepository.findByName(name).map(GuestMapper.GUEST_MAPPER_INSTANCE::map).orElse(new ArrayList<>());
	}

	@Override
	public String removeGuest(Long guestId) {
		try {
			guestRepository.deleteById(guestId);
		} catch (EmptyResultDataAccessException ex) {
			throw new EntityNotFoundException("Guest not found");
		}
		return "SUCCESS";
	}

	@Override
	public List<GuestDTO> findAllGuests() {
		return guestRepository.findAll().stream().map(GuestMapper.GUEST_MAPPER_INSTANCE::map)
				.collect(Collectors.toList());

	}

}
