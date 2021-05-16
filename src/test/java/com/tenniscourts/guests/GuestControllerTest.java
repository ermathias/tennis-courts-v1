package com.tenniscourts.guests;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class GuestControllerTest {

	private static final String GUEST_NAME = "guest1";
	private static final Long GUEST_ID = 9L;
	private static final String SUCEESS_STRING = "SUCCESS";

	@InjectMocks
	GuestController guestController;

	@Mock
	private GuestServiceImpl guestService;

	private GuestDTO guestDTO;

	@BeforeEach
	public void setUp() {
		guestDTO = buildGuestDTO();
	}

	@Test
	public void shouldReurnGuest() {
		doReturn(guestDTO).when(guestService).findGuestById(GUEST_ID);
		ResponseEntity<GuestDTO> responseEntity = guestController.findGuestById(GUEST_ID);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		GuestDTO guestDTO = responseEntity.getBody();
		assertNotNull(guestDTO);
		assertEquals(GUEST_ID, guestDTO.getId());
		assertEquals(GUEST_NAME, guestDTO.getName());

	}

	@Test
	public void shouldReurnGuestByName() {
		List<GuestDTO> guests = new ArrayList<>();
		guests.add(guestDTO);
		doReturn(guests).when(guestService).findGuestByName(GUEST_NAME);
		ResponseEntity<List<GuestDTO>> responseEntity = guestController.findGuestByName(GUEST_NAME);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		GuestDTO guestDTO = responseEntity.getBody().get(0);
		assertNotNull(guestDTO);
		assertEquals(GUEST_ID, guestDTO.getId());
		assertEquals(GUEST_NAME, guestDTO.getName());

	}

	@Test
	public void shouldReurnGuestWhenModify() {
		doReturn(guestDTO).when(guestService).modifyGuest(guestDTO);
		ResponseEntity<GuestDTO> responseEntity = guestController.modifyGuest(guestDTO);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		GuestDTO guestDTO = responseEntity.getBody();
		assertNotNull(guestDTO);
		assertEquals(GUEST_ID, guestDTO.getId());
		assertEquals(GUEST_NAME, guestDTO.getName());

	}

	@Test
	public void shouldReurnGuestsWhenFindAll() {
		List<GuestDTO> guests = new ArrayList<>();
		guests.add(guestDTO);
		doReturn(guests).when(guestService).findAllGuests();
		ResponseEntity<List<GuestDTO>> responseEntity = guestController.findAllGuest();
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		GuestDTO guestDTO = responseEntity.getBody().get(0);
		assertNotNull(guestDTO);
		assertEquals(GUEST_ID, guestDTO.getId());
		assertEquals(GUEST_NAME, guestDTO.getName());

	}

	@Test
	public void shouldReurnSuccessWhenRemove() {
		doReturn(SUCEESS_STRING).when(guestService).removeGuest(GUEST_ID);
		ResponseEntity<String> responseEntity = guestController.removeGuest(GUEST_ID);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		String status = responseEntity.getBody();
		assertNotNull(status);
		assertEquals(SUCEESS_STRING, status);

	}

	private GuestDTO buildGuestDTO() {
		GuestDTO guestDTO = new GuestDTO();
		guestDTO.setName(GUEST_NAME);
		guestDTO.setId(GUEST_ID);
		return guestDTO;
	}
}
