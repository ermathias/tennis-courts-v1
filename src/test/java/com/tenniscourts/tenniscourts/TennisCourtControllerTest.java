package com.tenniscourts.tenniscourts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tenniscourts.guests.GuestDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TennisCourtControllerTest {

    @InjectMocks
    private TennisCourtController tennisCourtController;

    @Mock
    private TennisCourtService tennisCourtService;

    @Autowired
    private MockMvc mockMvc;

    private final static Long COURT_ID = 1L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(tennisCourtController).build();
    }

    @Test
    public void test_findTennisCourtById_expect_ok_status() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/tennis-court/find/" + COURT_ID))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_findTennisCourtWithSchedulesById_expect_ok_status() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/tennis-court/find-with-schedule/" + COURT_ID))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
