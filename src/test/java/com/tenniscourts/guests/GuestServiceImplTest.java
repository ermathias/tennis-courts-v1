package com.tenniscourts.guests;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import com.tenniscourts.exceptions.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class GuestServiceImplTest {

	private static final String GUEST_NAME = "guest1";
	private static final Long GUEST_ID = 9L;
	private static final String SUCEESS_STRING = "SUCCESS";
	private static final String GUEST_NOT_FOUND = "Guest not found";

	@InjectMocks
	private GuestServiceImpl guestService;

	@Mock
	private GuestRepository guestRepository;

	private GuestRequest guestRequest;
	private GuestDTO guestDTO;
	private Guest guest;

	@BeforeEach
	public void setUp() {
		guestRequest = buildGuestRequest();
		guestDTO = buildGuestDTO();
		guest = buildGuest();
	}

	@Test
	public void shouldReurnGuestWhileAddGuest() {
		Guest guestInfo = new Guest();
		guestInfo.setName(GUEST_NAME);
		doReturn(guest).when(guestRepository).saveAndFlush(guestInfo);
		GuestDTO actual = guestService.addGuest(guestRequest);
		assertNotNull(actual);
		assertEquals(GUEST_ID, actual.getId());
		assertEquals(GUEST_NAME, actual.getName());

	}

	@Test
	public void shouldReurnGuestDTOWhileModifyGuest() {
		doReturn(Optional.of(guest)).when(guestRepository).findById(guestDTO.getId());
		doReturn(guest).when(guestRepository).saveAndFlush(Mockito.any(Guest.class));
		GuestDTO actual = guestService.modifyGuest(guestDTO);
		assertNotNull(actual);
		assertEquals(GUEST_ID, actual.getId());
		assertEquals(GUEST_NAME, actual.getName());

	}

	@Test
	public void shouldReurnGuestNotFoundWhileModifyGuest() {
		doThrow(new EntityNotFoundException(GUEST_NOT_FOUND)).when(guestRepository).findById(guestDTO.getId());
		try {
			guestService.modifyGuest(guestDTO);
		} catch (Exception ex) {
			assertNotNull(ex);
			assertEquals(GUEST_NOT_FOUND, ex.getMessage());
		}

	}

	@Test
	public void shouldReurnGuestDTOWhileFindGuestById() {
		doReturn(Optional.of(guest)).when(guestRepository).findById(guestDTO.getId());
		GuestDTO actual = guestService.findGuestById(guestDTO.getId());
		assertNotNull(actual);
		assertEquals(GUEST_ID, actual.getId());
		assertEquals(GUEST_NAME, actual.getName());

	}

	@Test
	public void shouldReurnGuestNotFoundWhileFindGuestById() {
		doReturn(Optional.empty()).when(guestRepository).findById(guestDTO.getId());
		try {
			guestService.findGuestById(guestDTO.getId());
		} catch (Exception ex) {
			assertNotNull(ex);
			assertEquals(GUEST_NOT_FOUND, ex.getMessage());
		}

	}

	@Test
	public void shouldReurnGuestsWhileFindGuestByName() {
		List<Guest> guests = new ArrayList<>();
		guests.add(guest);
		doReturn(Optional.of(guests)).when(guestRepository).findByName(GUEST_NAME);
		List<GuestDTO> actual = guestService.findGuestByName(GUEST_NAME);
		assertNotNull(actual);
		GuestDTO guestDTO = actual.get(0);
		assertEquals(GUEST_ID, guestDTO.getId());
		assertEquals(GUEST_NAME, guestDTO.getName());
	}

	@Test
	public void shouldReurnSuccessWhileRemoveGuest() {
		doNothing().when(guestRepository).deleteById(GUEST_ID);
		String actual = guestService.removeGuest(GUEST_ID);
		assertNotNull(actual);
		assertEquals(SUCEESS_STRING, actual);
	}

	@Test
	public void shouldReurnGuestNotFoundWhileRemoveGuest() {
		doThrow(new EmptyResultDataAccessException(1)).when(guestRepository).deleteById(GUEST_ID);
		try {
			guestService.removeGuest(GUEST_ID);
		} catch (EntityNotFoundException ex) {
			assertNotNull(ex);
			assertEquals(GUEST_NOT_FOUND, ex.getMessage());
		}

	}

	@Test
	public void shouldReurnGuestsWhileFindAllGuest() {
		List<Guest> guests = new ArrayList<>();
		guests.add(guest);
		doReturn(guests).when(guestRepository).findAll();
		List<GuestDTO> actual = guestService.findAllGuests();
		assertNotNull(actual);
		GuestDTO guestDTO = actual.get(0);
		assertEquals(GUEST_ID, guestDTO.getId());
		assertEquals(GUEST_NAME, guestDTO.getName());
	}

	private GuestRequest buildGuestRequest() {
		GuestRequest guestRquest = new GuestRequest();
		guestRquest.setName(GUEST_NAME);
		return guestRquest;
	}

	private GuestDTO buildGuestDTO() {
		GuestDTO guestDTO = new GuestDTO();
		guestDTO.setName(GUEST_NAME);
		guestDTO.setId(GUEST_ID);
		return guestDTO;
	}

	private Guest buildGuest() {
		Guest guest = new Guest();
		guest.setName(GUEST_NAME);
		guest.setId(GUEST_ID);
		return guest;
	}
}
