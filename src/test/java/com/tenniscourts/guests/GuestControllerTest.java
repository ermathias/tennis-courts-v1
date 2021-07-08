package com.tenniscourts.guests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
public class GuestControllerTest {

    @InjectMocks
    private GuestController guestController;

    @Mock
    private GuestService guestService;

    @Autowired
    private MockMvc mockMvc;

    private final static String GUEST_NAME_PARAMETER = "guestName";
    private final static String GUEST_NAME = "Marc";

    private final static String GUEST_ID_PARAMETER = "guestId";
    private final static Long GUEST_ID = 1L;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(guestController).build();
    }

    @Test
    public void test_findAllGuests__expect_ok_status() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/guest/find"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_findGuestByName_expect_ok_status() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/guest/find")
                .queryParam(GUEST_NAME_PARAMETER, new String[]{GUEST_NAME}))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_findGuestById_expect_ok_status() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/guest/find")
                .queryParam(GUEST_ID_PARAMETER, new String[]{GUEST_ID.toString()}))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_updateGuest_expect_ok_status() throws Exception {
        GuestDTO guest = new GuestDTO();
        guest.setId(GUEST_ID);
        guest.setName(GUEST_NAME);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();

        String requestJson = writer.writeValueAsString(guest);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/guest/update/" + GUEST_ID)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_removeGuest_expect_ok_status() throws Exception {
        GuestDTO guest = new GuestDTO();
        guest.setId(GUEST_ID);
        guest.setName(GUEST_NAME);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();

        String requestJson = writer.writeValueAsString(guest);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/guest/remove/" + GUEST_ID)
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
