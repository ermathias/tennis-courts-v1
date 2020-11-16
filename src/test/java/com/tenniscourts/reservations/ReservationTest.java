package com.tenniscourts.reservations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenniscourts.guests.GuestController;
import com.tenniscourts.schedules.ScheduleController;
import com.tenniscourts.tenniscourts.TennisCourtController;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MockMvc mockMvcGuest;
    @Autowired
    private MockMvc mockMvcSchedule;
    @Autowired
    private MockMvc mockMvcTennisCourt;
    @Autowired
    private GuestController guestController;
    @Autowired
    private ReservationController reservationController;
    @Autowired
    private ScheduleController scheduleController;
    @Autowired
    private TennisCourtController tennisCourtController;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.mockMvcTennisCourt = MockMvcBuilders.standaloneSetup(tennisCourtController).build();
        this.mockMvcSchedule = MockMvcBuilders.standaloneSetup(scheduleController).build();
        this.mockMvcGuest = MockMvcBuilders.standaloneSetup(guestController).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }

    @Test
    public void testGETIndexReservation() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/reservations/all"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testPOSTIndexReservationsMustNotReceivePostWithoutParameters() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/reservations"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void shouldCreateAReservation() throws Exception {

        CreateReservationRequestDTO createReservationRequestDTO = CreateReservationRequestDTO.builder()
                .guestId(Long.valueOf(1)).scheduleId(Long.valueOf(1)).build();

        mockMvc.perform(post("/reservations").contentType("application/json")
                .content(objectMapper.writeValueAsString(createReservationRequestDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldCreateMultipleAReservation() throws Exception {
        CreateReservationRequestDTO createReservationRequestDTO1 = CreateReservationRequestDTO.builder()
                .guestId(Long.valueOf(1)).scheduleId(Long.valueOf(1)).build();

        mockMvc.perform(post("/reservations").contentType("application/json")
                .content(objectMapper.writeValueAsString(createReservationRequestDTO1)))
                .andExpect(status().isCreated());

        CreateReservationRequestDTO createReservationRequestDTO2 = CreateReservationRequestDTO.builder()
                .guestId(Long.valueOf(1)).scheduleId(Long.valueOf(2)).build();

        mockMvc.perform(post("/reservations").contentType("application/json")
                .content(objectMapper.writeValueAsString(createReservationRequestDTO2)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldRescheduleAReservation() throws Exception {

        // RESCHEDULE
        this.mockMvc.perform(MockMvcRequestBuilders.patch("/reservations/1/reschedule/2"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}