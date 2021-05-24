package com.tenniscourts.guests;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = GuestController.class)
public class GuestControllerTest {

    @MockBean
    private GuestService guestService;

    @Autowired
    private MockMvc mvc;

    static String GUEST_API = "/api/guests";

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Must save a guest in the database and return status code 201")
    public void save() throws Exception{

        GuestDTO guestDTO = new GuestDTO.GuestDTOBuilder().name("João").build();

        String json = new ObjectMapper().writeValueAsString(guestDTO);

        GuestDTO responseGuestDTO = new GuestDTO.GuestDTOBuilder().id(10L).name("João").build();

        BDDMockito.given(guestService.save(Mockito.any(GuestDTO.class))).willReturn(responseGuestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(GUEST_API.concat("/add"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Must return a list of all the Guests")
    public void getAll() throws Exception {
        GuestDTO guestDTO = new GuestDTO.GuestDTOBuilder().name("João").build();
        List<GuestDTO> guestDTOList = Arrays.asList(guestDTO);

        BDDMockito.given(guestService.getAll()).willReturn(guestDTOList);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(GUEST_API);

        mvc
                .perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Must return a Guest by name or a part of the name")
    public void findByName() throws Exception {
        GuestDTO guestDTO = new GuestDTO.GuestDTOBuilder().name("João").build();
        List<GuestDTO> guestDTOList = Arrays.asList(guestDTO);

        BDDMockito.given(guestService.findByNameContaining(Mockito.anyString())).willReturn(guestDTOList);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(GUEST_API.concat("/name/1"))
                .accept(MediaType.APPLICATION_JSON);

        mvc
                .perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Must return a guest by id")
    public void findById() throws Exception {
        GuestDTO guestDTO = new GuestDTO.GuestDTOBuilder().id(1L).name("João").build();

        BDDMockito.given(guestService.findById(Mockito.anyLong())).willReturn(guestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(GUEST_API.concat("/id/1"))
                .accept(MediaType.APPLICATION_JSON);

        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("João"));
    }

    @Test
    @DisplayName("Must delete a guest by id and return status code 200")
    public void delete() throws Exception {
        Mockito.doNothing().when(guestService).delete(Mockito.anyLong());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(GUEST_API.concat("/delete/1"))
                .accept(MediaType.APPLICATION_JSON);

        mvc
                .perform(request)
                .andExpect(status().isOk());
    }

}
