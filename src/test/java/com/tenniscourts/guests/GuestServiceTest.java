package com.tenniscourts.guests;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.logging.log4j.util.Strings.EMPTY;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.dto.CreateGuestRequestDTO;
import com.tenniscourts.guests.dto.GuestDTO;
import com.tenniscourts.guests.dto.UpdateGuestRequestDTO;

@ContextConfiguration(classes = GuestService.class)
@RunWith(MockitoJUnitRunner.class)
public class GuestServiceTest {
	
	private static final String GUEST_1_NAME = "John";
	private static final String GUEST_1_NEW_NAME = "John Travolta";
	private static final Long GUEST_1_ID = 1l;
	
	@Mock 
	GuestRepository guestRepository;  
	
	@Mock 
	GuestMapper guestMapper;  
	
	@InjectMocks
	GuestService guestService;
	
	@Test
	public void create_SuccessTest() {
		
		Guest guest = Guest.builder().name(GUEST_1_NAME).id(GUEST_1_ID).build();
		
		Mockito.when(guestRepository.save(Mockito.any())).thenReturn(guest);
		
		Mockito.when(guestMapper.mapToEntity(Mockito.any(CreateGuestRequestDTO.class)))
				.thenReturn(Guest.builder().name(GUEST_1_NAME).build());
		
		Mockito.when(guestMapper.mapToDTO(Mockito.any()))
				.thenReturn(GuestDTO.builder().name(GUEST_1_NAME).id(GUEST_1_ID).build());
		
		CreateGuestRequestDTO createGuestDTO = CreateGuestRequestDTO.builder().name(GUEST_1_NAME).build();
 		GuestDTO createdGuest = guestService.create(createGuestDTO);
 		Assert.assertEquals(guest.getName(), createdGuest.getName());
 		Assert.assertEquals(guest.getId(), createdGuest.getId());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void create_NullCreateObjectTest() {
 		guestService.create(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void create_NullNameTest() {
 		guestService.create(CreateGuestRequestDTO.builder().name(null).build());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void create_EmptyNameTest() {
 		guestService.create(CreateGuestRequestDTO.builder().name(EMPTY).build());
	}
	
	@Test
	public void update_SuccessTest() {
		
		Guest guest = Guest.builder().name(GUEST_1_NAME).id(GUEST_1_ID).build();
		Guest guestWithNewName = Guest.builder().name(GUEST_1_NEW_NAME).id(GUEST_1_ID).build();
		
		Mockito.when(guestRepository.findById(Mockito.any())).thenReturn(Optional.of(guest));
		
		Mockito.when(guestRepository.save(Mockito.any())).thenReturn(guestWithNewName);
		
		Mockito.when(guestMapper.mapToEntity(Mockito.any(UpdateGuestRequestDTO.class)))
				.thenReturn(Guest.builder().name(GUEST_1_NEW_NAME).id(GUEST_1_ID).build());
		
		UpdateGuestRequestDTO updateDTO = UpdateGuestRequestDTO.builder().name(GUEST_1_NEW_NAME).id(GUEST_1_ID).build();
 		guestService.update(updateDTO);
 		Assert.assertTrue(true);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void update_NullCreateObjectTest() {
 		guestService.update(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void update_NullNameTest() {
 		guestService.update(UpdateGuestRequestDTO.builder().name(null).build());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void update_EmptyNameTest() {
 		guestService.update(UpdateGuestRequestDTO.builder().name(EMPTY).build());
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void update_NotFoundTest() {
		Mockito.when(guestRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		UpdateGuestRequestDTO updateDTO = UpdateGuestRequestDTO.builder().name(GUEST_1_NEW_NAME).id(GUEST_1_ID).build();
 		guestService.update(updateDTO);
	}
	
	@Test
	public void delete_SuccessTest() {
		Guest guest = Guest.builder().name(GUEST_1_NAME).id(GUEST_1_ID).build();
		Mockito.when(guestRepository.findById(Mockito.any())).thenReturn(Optional.of(guest));
 		guestService.delete(GUEST_1_ID);
 		Assert.assertTrue(true);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void delete_NoGuestIdTest() {
 		guestService.delete(null);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void delete_NotFoundTest() {
		Mockito.when(guestRepository.findById(Mockito.any())).thenReturn(Optional.empty());
 		guestService.delete(GUEST_1_ID);
	}
	
	@Test
	public void findById_SuccessTest() {
		
		Guest guest = Guest.builder().name(GUEST_1_NAME).id(GUEST_1_ID).build();
		
		Mockito.when(guestRepository.findById(Mockito.any())).thenReturn(Optional.of(guest));
		
		Mockito.when(guestMapper.mapToDTO(Mockito.any(Guest.class)))
				.thenReturn(GuestDTO.builder().name(GUEST_1_NAME).id(GUEST_1_ID).build());
		
 		GuestDTO foundGuest = guestService.findById(GUEST_1_ID);
 		Assert.assertEquals(foundGuest.getName(), guest.getName());
 		Assert.assertEquals(foundGuest.getId(), guest.getId());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void findById_NullGuestIdTest() {
 		guestService.findById(null);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void findById_NotFoundTest() {
		Mockito.when(guestRepository.findById(Mockito.any())).thenReturn(Optional.empty());
 		guestService.findById(GUEST_1_ID);
	}
	
	@Test
	public void findByName_SuccessTest() {
		
		Guest guest = Guest.builder().name(GUEST_1_NAME).id(GUEST_1_ID).build();
		
		Mockito.when(guestMapper.mapToDTO(Mockito.any(Guest.class)))
				.thenReturn(GuestDTO.builder().name(GUEST_1_NAME).id(GUEST_1_ID).build());
		
		Mockito.when(guestRepository.findByNameContains(Mockito.any())).thenReturn(newArrayList(guest));
		
 		List<GuestDTO> guests = guestService.findByName(GUEST_1_NAME);
 		Assert.assertEquals(1, guests.size());
 		Assert.assertEquals(GUEST_1_NAME, guests.get(0).getName());
 		Assert.assertEquals(GUEST_1_ID, guests.get(0).getId());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void findByName_NullGuestNameTest() {
 		guestService.findByName(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void findByName_EmptyGuestNameTest() {
 		guestService.findByName(EMPTY);
	}
	

}
