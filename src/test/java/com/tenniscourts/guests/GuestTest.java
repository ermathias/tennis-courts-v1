package com.tenniscourts.guests;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GuestTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private GuestController guestController;

        @Before
        public void setUp() {
                this.mockMvc = MockMvcBuilders.standaloneSetup(guestController).build();
        }

        @Test
        public void testGETIndexGuests() throws Exception {
                this.mockMvc.perform(MockMvcRequestBuilders.get("/guests"))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testPOSTIndexGuestsMustNotReceivePostWithoutParameters() throws Exception {
                this.mockMvc.perform(MockMvcRequestBuilders.post("/guests"))
                                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void shouldCreateAGuest() throws Exception {
                GuestDTO guestDTO = GuestDTO.builder().name("A name for tennis court").build();
                mockMvc.perform(post("/guests").contentType("application/json")
                                .content(objectMapper.writeValueAsString(guestDTO))).andExpect(status().isCreated());
        }

        @Test
        void shouldUpdateAGuest() throws Exception {
                
                GuestDTO updateGuestDTO = GuestDTO.builder().name("A new name for guest").id(Long.valueOf(1)).build();
                mockMvc.perform(post("/guests").contentType("application/json")
                                .content(objectMapper.writeValueAsString(updateGuestDTO)))
                                .andExpect(status().isCreated());
        }

        @Test
        void shouldFindTennisCourtForId() throws Exception {

                this.mockMvc.perform(MockMvcRequestBuilders.get("/guests/1"))
                                .andExpect(MockMvcResultMatchers.status().isOk());

                this.mockMvc.perform(MockMvcRequestBuilders.get("/guests/2"))
                                .andExpect(MockMvcResultMatchers.status().isOk());

                this.mockMvc.perform(MockMvcRequestBuilders.get("/guests/3"))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

}