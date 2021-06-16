package com.tenniscourts.users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenniscourts.UriConstants;
import com.tenniscourts.guests.CreateGuestRequestDTO;
import com.tenniscourts.guests.GuestController;
import com.tenniscourts.guests.GuestDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GuestController guestController;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp()
    {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.guestController).build();
    }

    @Test
    @Order(1)
    @DisplayName("As a Tennis Court Admin, I want to be able to create a guest")
    public void createGuestTest() throws Exception {
        // Given
        CreateGuestRequestDTO createGuestRequestDTO = CreateGuestRequestDTO.builder().name("testName").build();

        // When
        mockMvc.perform(post(UriConstants.GUEST_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(createGuestRequestDTO)))
                // Then
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    @DisplayName("As a Tennis Court Admin, I want to be able to update a guest")
    public void updateGuestTest() throws Exception {
        // Given
        final String newName = "newTestName";
        final long guestId = 1L;

        // When
        mockMvc.perform(put(UriConstants.GUEST_PATH + "/" + guestId + "/" + newName))
                // Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(newName));
    }

    @Test
    @Order(3)
    @DisplayName("As a Tennis Court Admin, I want to be able to delete a guest")
    public void deleteGuestTest() throws Exception {
        // Given
        final long guestId = 2L;

        // When
        mockMvc.perform(delete(UriConstants.GUEST_PATH + "/" + guestId ))
                // Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(guestId));
    }

    @Test
    @Order(4)
    @DisplayName("As a Tennis Court Admin, I want to be able to find by id a guest")
    public void findAGuestByIdTest() throws Exception {
        // Given
        final long guestId = 1L;
        String expectedName = "newTestName";

        // When
        mockMvc.perform(get(UriConstants.GUEST_PATH + "/" + guestId ))
                // Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(guestId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedName));
    }

    @Test
    @Order(5)
    @DisplayName("As a Tennis Court Admin, I want to be able to find all guests")
    public void findAllGuestsTest() throws Exception {
        // Given
        CreateGuestRequestDTO createGuestRequestDTO1 = CreateGuestRequestDTO.builder().name("Andrew").build();
        CreateGuestRequestDTO createGuestRequestDTO2 = CreateGuestRequestDTO.builder().name("Andra").build();
        CreateGuestRequestDTO createGuestRequestDTO3 = CreateGuestRequestDTO.builder().name("Bjorn").build();
        CreateGuestRequestDTO createGuestRequestDTO4 = CreateGuestRequestDTO.builder().name("Stacy").build();

        mockMvc.perform(post(UriConstants.GUEST_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(createGuestRequestDTO1)));

        mockMvc.perform(post(UriConstants.GUEST_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(createGuestRequestDTO2)));

        mockMvc.perform(post(UriConstants.GUEST_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(createGuestRequestDTO3)));

        mockMvc.perform(post(UriConstants.GUEST_PATH).contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(createGuestRequestDTO4)));

        // When
        MvcResult result = mockMvc.perform(get(UriConstants.GUEST_PATH + UriConstants.ALL_PATH)).andExpect(status().isOk()).andReturn();

        // Then
        List<GuestDTO> expectedGuestDTOList = Arrays.asList(GuestDTO.builder().id(1L).name("newTestName").build(),
                GuestDTO.builder().id(3L).name("Andrew").build(),
                GuestDTO.builder().id(4L).name("Andra").build(),
                GuestDTO.builder().id(5L).name("Bjorn").build(),
                GuestDTO.builder().id(6L).name("Stacy").build());

        List<GuestDTO> guestDTOList = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<GuestDTO>>() {});

        Assert.assertEquals(guestDTOList, expectedGuestDTOList);
    }

    @Test
    @Order(6)
    @DisplayName("As a Tennis Court Admin, I want to be able to find guests by name")
    public void findGuestsByNameTest() throws Exception {
        // Given
        String queryName = "An";

        // When
        MvcResult result = mockMvc.perform(get(UriConstants.GUEST_PATH + UriConstants.NAME_PATH + "/" + queryName))
                .andExpect(status().isOk()).andReturn();

        // Then
        List<GuestDTO> expectedGuestDTOList = Arrays.asList(GuestDTO.builder().id(3L).name("Andrew").build(),
                GuestDTO.builder().id(4L).name("Andra").build());

        List<GuestDTO> guestDTOList = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<GuestDTO>>() {});

        Assert.assertEquals(guestDTOList, expectedGuestDTOList);
    }
}
