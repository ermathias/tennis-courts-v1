package com.tenniscourts.guests;

import static com.google.common.collect.Lists.newArrayList;
import static com.tenniscourts.utils.TennisCourtsConstraints.CREATE_PATH;
import static com.tenniscourts.utils.TennisCourtsConstraints.DELETE_PATH;
import static com.tenniscourts.utils.TennisCourtsConstraints.FINDBYID_PATH;
import static com.tenniscourts.utils.TennisCourtsConstraints.FINDBYNAME_PATH;
import static com.tenniscourts.utils.TennisCourtsConstraints.GUEST_API_PATH;
import static com.tenniscourts.utils.TennisCourtsConstraints.LIST_PATH;
import static com.tenniscourts.utils.TennisCourtsConstraints.UPDATE_PATH;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.dto.CreateGuestRequestDTO;
import com.tenniscourts.guests.dto.GuestDTO;
import com.tenniscourts.guests.dto.UpdateGuestRequestDTO;

@RunWith(SpringRunner.class)
@WebMvcTest(GuestController.class)
public class GuestControllerTest {

	private static final String GUEST_1_NAME = "John";
	private static final Long GUEST_1_ID = 1l;
	
	private static final String GUEST_2_NAME = "Mary";
	private static final Long GUEST_2_ID = 2l;

	@MockBean
	GuestService guestService;

	@Autowired
	private MockMvc mvc;

	@Test
	public void create_SuccessTest() throws Exception {

		GuestDTO guestDTO = GuestDTO.builder().name(GUEST_1_NAME).id(GUEST_1_ID).build();
		Mockito.when(guestService.create(Mockito.any())).thenReturn(guestDTO);
		
		CreateGuestRequestDTO createDTO = CreateGuestRequestDTO.builder().name(GUEST_1_NAME).build();
		String responseString = mvc.perform(
				MockMvcRequestBuilders.post(GUEST_API_PATH + CREATE_PATH)
					.content(new Gson().toJson(createDTO))
					.contentType(APPLICATION_JSON)
					.characterEncoding(UTF_8.displayName())
					.accept(APPLICATION_JSON)
					)
		.andDo(print())
		.andExpect(status().isCreated())
		.andReturn().getResponse().getContentAsString();
		
		Long createdGuestId = new ObjectMapper().readValue(responseString, new TypeReference<Long>(){});
		assertTrue(createdGuestId.equals(GUEST_1_ID));
	}
	
	@Test
	public void create_NullNameTest() throws Exception {

		CreateGuestRequestDTO createDTO = CreateGuestRequestDTO.builder().name(null).build();
		
		mvc.perform(
				MockMvcRequestBuilders.post(GUEST_API_PATH + CREATE_PATH)
					.content(new Gson().toJson(createDTO))
					.contentType(APPLICATION_JSON)
					.characterEncoding(UTF_8.displayName())
					.accept(APPLICATION_JSON)
					)
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void create_BlankNameTest() throws Exception {

		CreateGuestRequestDTO createDTO = CreateGuestRequestDTO.builder().name(EMPTY).build();
		
		mvc.perform(
				MockMvcRequestBuilders.post(GUEST_API_PATH + CREATE_PATH)
					.content(new Gson().toJson(createDTO))
					.contentType(APPLICATION_JSON)
					.characterEncoding(UTF_8.displayName())
					.accept(APPLICATION_JSON)
					)
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void update_SuccessTest() throws Exception {

		UpdateGuestRequestDTO createDTO = UpdateGuestRequestDTO.builder().name(GUEST_1_NAME).id(GUEST_1_ID).build();
		mvc.perform(
				MockMvcRequestBuilders.put(GUEST_API_PATH + UPDATE_PATH)
					.content(new Gson().toJson(createDTO))
					.contentType(APPLICATION_JSON)
					.characterEncoding(UTF_8.displayName())
					.accept(APPLICATION_JSON)
					)
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void update_NullNameTest() throws Exception {

		UpdateGuestRequestDTO createDTO = UpdateGuestRequestDTO.builder().name(null).id(GUEST_1_ID).build();
		mvc.perform(
				MockMvcRequestBuilders.put(GUEST_API_PATH + UPDATE_PATH)
					.content(new Gson().toJson(createDTO))
					.contentType(APPLICATION_JSON)
					.characterEncoding(UTF_8.displayName())
					.accept(APPLICATION_JSON)
					)
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void update_BlankNameTest() throws Exception {

		UpdateGuestRequestDTO createDTO = UpdateGuestRequestDTO.builder().name(EMPTY).id(GUEST_1_ID).build();
		mvc.perform(
				MockMvcRequestBuilders.put(GUEST_API_PATH + UPDATE_PATH)
					.content(new Gson().toJson(createDTO))
					.contentType(APPLICATION_JSON)
					.characterEncoding(UTF_8.displayName())
					.accept(APPLICATION_JSON)
					)
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void delete_SuccessTest() throws Exception {
		
		mvc.perform(
				MockMvcRequestBuilders.delete(GUEST_API_PATH + DELETE_PATH + "/1")
					.accept(MediaType.APPLICATION_JSON)
					)
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void delete_NotFoundTest() throws Exception {
		
		Mockito.doThrow(new EntityNotFoundException("Not found")).when(this.guestService).delete(Mockito.any());
		
		mvc.perform(
				MockMvcRequestBuilders.delete(GUEST_API_PATH + DELETE_PATH + "/1")
					.accept(MediaType.APPLICATION_JSON)
					)
		.andDo(print())
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void findById_SuccessTest() throws Exception {
		
		GuestDTO guestDTO = GuestDTO.builder().name(GUEST_1_NAME).id(GUEST_1_ID).build();
		Mockito.when(guestService.findById(Mockito.any())).thenReturn(guestDTO);
		
		String responseString = mvc.perform(
				MockMvcRequestBuilders.get(GUEST_API_PATH + FINDBYID_PATH + "/1")
					.accept(MediaType.APPLICATION_JSON)
					)
		.andDo(print())
		.andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
		
		GuestDTO returnedGuest = new ObjectMapper().readValue(responseString, new TypeReference<GuestDTO>(){});
		assertTrue(returnedGuest.equals(guestDTO));
	}
	
	@Test
	public void findById_NotFoundTest() throws Exception {
		
		Mockito.when(guestService.findById(Mockito.any())).thenThrow(EntityNotFoundException.class);
		
		mvc.perform(
				MockMvcRequestBuilders.get(GUEST_API_PATH + FINDBYID_PATH + "/1")
					.accept(MediaType.APPLICATION_JSON)
					)
		.andDo(print())
		.andExpect(status().isNotFound());
	}
	
	
	@Test
	public void findByName_SuccessTest() throws Exception {
		
		GuestDTO guestDTO = GuestDTO.builder().name(GUEST_1_NAME).id(GUEST_1_ID).build();
		Mockito.when(guestService.findByName(Mockito.any())).thenReturn(newArrayList(guestDTO));
		
		String responseString = mvc.perform(
				MockMvcRequestBuilders.get(GUEST_API_PATH + FINDBYNAME_PATH + "/" + GUEST_1_NAME)
					.accept(MediaType.APPLICATION_JSON)
					)
		.andDo(print())
		.andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
		
		List<GuestDTO> guestList = new ObjectMapper().readValue(responseString, new TypeReference<List<GuestDTO>>(){});
		assertTrue(guestList.size() == 1);
		assertTrue(guestList.contains(guestDTO));
	}
	
	@Test
	public void findByName_NotFoundTest() throws Exception {
		
		Mockito.when(guestService.findByName(Mockito.any())).thenReturn(newArrayList());
		
		String responseString = mvc.perform(
				MockMvcRequestBuilders.get(GUEST_API_PATH + FINDBYNAME_PATH + "/" + GUEST_1_NAME)
					.accept(MediaType.APPLICATION_JSON)
					)
		.andDo(print())
		.andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
		
		List<GuestDTO> guestList = new ObjectMapper().readValue(responseString, new TypeReference<List<GuestDTO>>(){});
		assertTrue(guestList.isEmpty());
	}
	
	@Test
	public void list_SuccessTest() throws Exception {
		
		GuestDTO guestDTO1 = GuestDTO.builder().name(GUEST_1_NAME).id(GUEST_1_ID).build();
		GuestDTO guestDTO2 = GuestDTO.builder().name(GUEST_2_NAME).id(GUEST_2_ID).build();
		Mockito.when(guestService.list()).thenReturn(newArrayList(guestDTO1, guestDTO2));
		
		String responseString = mvc.perform(
				MockMvcRequestBuilders.get(GUEST_API_PATH + LIST_PATH)
					.accept(MediaType.APPLICATION_JSON)
					)
		.andDo(print())
		.andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
		
		List<GuestDTO> guestList = new ObjectMapper().readValue(responseString, new TypeReference<List<GuestDTO>>(){});
		assertTrue(guestList.size() == 2);
		assertTrue(guestList.contains(guestDTO1));
		assertTrue(guestList.contains(guestDTO2));
	}

}
