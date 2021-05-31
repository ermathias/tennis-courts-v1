package com.tenniscourts.guests;

import static com.tenniscourts.utils.TennisCourtsConstraints.NOT_FOUND_GUEST;
import static com.tenniscourts.utils.TennisCourtsConstraints.NOT_INFORMED_GUEST;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static org.apache.logging.log4j.util.Strings.isBlank;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.dto.CreateGuestRequestDTO;
import com.tenniscourts.guests.dto.GuestDTO;
import com.tenniscourts.guests.dto.UpdateGuestRequestDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

	public GuestDTO create(CreateGuestRequestDTO dto) {
		validateCreation(dto);
		return guestMapper.mapToDTO( this.guestRepository.save(guestMapper.mapToEntity(dto)));
	}
	
	private void validateCreation(CreateGuestRequestDTO dto) {
		if(isNull(dto) || isBlank(dto.getName()))
			throw new IllegalArgumentException(NOT_INFORMED_GUEST);
	}

	public List<GuestDTO> list() {
		return this.guestRepository.findAll().stream().map( entity -> guestMapper.mapToDTO(entity)).collect(toList());
	}

	public void update(UpdateGuestRequestDTO dto) {
		validateUpdate(dto);
		this.guestRepository.save(guestMapper.mapToEntity(dto));
	}
	
	private void validateUpdate(UpdateGuestRequestDTO dto) {
		if(isNull(dto) || isBlank(dto.getName()))
			throw new IllegalArgumentException(NOT_INFORMED_GUEST);
		
		validateExistenceAndFindEntityById(dto.getId());
	}

	public void delete(Long guestId) {
		validateDelete(guestId);
		this.guestRepository.deleteById(guestId);
	}
	
	private void validateDelete(Long guestId) {
		if(isNull(guestId))
			throw new IllegalArgumentException(NOT_INFORMED_GUEST);
		validateExistenceAndFindEntityById(guestId);
	}

	public GuestDTO findById(Long guestId) {
		validateFindById(guestId);
		return guestMapper.mapToDTO(validateExistenceAndFindEntityById(guestId));
	}
	
	private void validateFindById(Long guestId) {
		if(isNull(guestId))
			throw new IllegalArgumentException(NOT_INFORMED_GUEST);
	}
	
	public List<GuestDTO> findByName(String guestName) {
		validateFindByName(guestName);
		return this.guestRepository.findByNameContains(guestName).stream().map(entity -> guestMapper.mapToDTO(entity)).collect(toList());
	}
	
	private void validateFindByName(String guestName) {
		if(isBlank(guestName))
			throw new IllegalArgumentException(NOT_INFORMED_GUEST);
	}
	
	private Guest validateExistenceAndFindEntityById(Long guestId) {
		return this.guestRepository.findById(guestId)
				.<EntityNotFoundException>orElseThrow( () ->  new EntityNotFoundException(NOT_FOUND_GUEST) );
	}
	
}
