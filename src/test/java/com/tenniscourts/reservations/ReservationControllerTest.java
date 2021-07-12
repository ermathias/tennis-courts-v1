package com.tenniscourts.reservations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tenniscourts.guests.GuestDTO;
import com.tenniscourts.tenniscourts.TennisCourtController;
import com.tenniscourts.tenniscourts.TennisCourtService;
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
public class ReservationControllerTest {

    @InjectMocks
    private ReservationController reservationController;

    @Mock
    private ReservationService reservationService;

    @Autowired
    private MockMvc mockMvc;

    private final static Long GUEST_ID = 1L;
    private final static Long SCHEDULE_ID = 1L;
    private final static Long RESERVATION_ID = 1L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }

    @Test
    public void test_findReservation_expect_ok_status() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/reservation/find/" + RESERVATION_ID))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_cancelReservation_expect_ok_status() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/reservation/cancel/" + RESERVATION_ID))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_rescheduleReservation_expect_ok_status() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = writer.writeValueAsString(SCHEDULE_ID);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/reservation/cancel/" + RESERVATION_ID)
        .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
