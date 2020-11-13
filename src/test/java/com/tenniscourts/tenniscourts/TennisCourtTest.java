package com.tenniscourts.tenniscourts;

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

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TennisCourtTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private TennisCourtController tennisCourtController;

        @Before
        public void setUp() {
                this.mockMvc = MockMvcBuilders.standaloneSetup(tennisCourtController).build();
        }

        // Test CRUD

        @Test
        public void testGETIndexTennisCourts() throws Exception {
                this.mockMvc.perform(MockMvcRequestBuilders.get("/tennis-courts"))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        public void testPOSTIndexTennisCourtsMustNotReceivePostWithoutParameters() throws Exception {
                this.mockMvc.perform(MockMvcRequestBuilders.post("/tennis-courts"))
                                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        }

        @Test
        void shouldCreateATennisCourt() throws Exception {
                TennisCourtDTO tennisCourtDTO = TennisCourtDTO.builder().name("A name for tennis court")
                                .tennisCourtSchedules(new ArrayList<>()).build();
                mockMvc.perform(post("/tennis-courts").contentType("application/json")
                                .content(objectMapper.writeValueAsString(tennisCourtDTO)))
                                .andExpect(status().isCreated());
        }

        @Test
        void shouldUpdateATennisCourt() throws Exception {
                TennisCourtDTO tennisCourtDTO = TennisCourtDTO.builder().name("A name for tennis court")
                                .tennisCourtSchedules(new ArrayList<>()).build();
                mockMvc.perform(post("/tennis-courts").contentType("application/json")
                                .content(objectMapper.writeValueAsString(tennisCourtDTO)))
                                .andExpect(status().isCreated());

                TennisCourtDTO updateTennisCourtDTO = TennisCourtDTO.builder().name("A new name for tennis court")
                                .id(Long.valueOf(1)).tennisCourtSchedules(new ArrayList<>()).build();
                mockMvc.perform(post("/tennis-courts").contentType("application/json")
                                .content(objectMapper.writeValueAsString(updateTennisCourtDTO)))
                                .andExpect(status().isCreated());
        }

        @Test
        void shouldFindTennisCourtForId() throws Exception {

                TennisCourtDTO tennisCourtDTO1 = TennisCourtDTO.builder().name("TesteTennisCourt1")
                                .tennisCourtSchedules(new ArrayList<>()).build();

                TennisCourtDTO tennisCourtDTO2 = TennisCourtDTO.builder().name("TesteTennisCourt2")
                                .tennisCourtSchedules(new ArrayList<>()).build();

                TennisCourtDTO tennisCourtDTO3 = TennisCourtDTO.builder().name("TesteTennisCourt3")
                                .tennisCourtSchedules(new ArrayList<>()).build();

                mockMvc.perform(post("/tennis-courts").contentType("application/json")
                                .content(objectMapper.writeValueAsString(tennisCourtDTO1)))
                                .andExpect(status().isCreated());
                mockMvc.perform(post("/tennis-courts").contentType("application/json")
                                .content(objectMapper.writeValueAsString(tennisCourtDTO2)))
                                .andExpect(status().isCreated());
                mockMvc.perform(post("/tennis-courts").contentType("application/json")
                                .content(objectMapper.writeValueAsString(tennisCourtDTO3)))
                                .andExpect(status().isCreated());

                this.mockMvc.perform(MockMvcRequestBuilders.get("/tennis-courts/1"))
                                .andExpect(MockMvcResultMatchers.status().isOk());

                this.mockMvc.perform(MockMvcRequestBuilders.get("/tennis-courts/2"))
                                .andExpect(MockMvcResultMatchers.status().isOk());

                this.mockMvc.perform(MockMvcRequestBuilders.get("/tennis-courts/3"))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

}