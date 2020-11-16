package com.tenniscourts.tenniscourts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TennisCourtTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TennisCourtController tennisCourtController;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(tennisCourtController).build();
    }

    @Test
    public void testGETIndexTennisCourts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tennis-courts"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testPOSTIndexTennisCourtsMustNotReceivePostWithoutParameters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/tennis-courts"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void shouldCreateATennisCourt() throws Exception {
        TennisCourtDTO tennisCourtDTO = TennisCourtDTO.builder()
                .name("Some Tennis Court")
                .tennisCourtSchedules(new ArrayList<>()).build();

        mockMvc.perform(post("/tennis-courts").contentType("application/json")
                .content(objectMapper.writeValueAsString(tennisCourtDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdateATennisCourt() throws Exception {
        TennisCourtDTO updateTennisCourtDTO = TennisCourtDTO.builder()
                .name("A new Tennis Court")
                .id(Long.valueOf(1))
                .tennisCourtSchedules(new ArrayList<>()).build();

        mockMvc.perform(post("/tennis-courts").contentType("application/json")
                .content(objectMapper.writeValueAsString(updateTennisCourtDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldFindTennisCourtForId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tennis-courts/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}