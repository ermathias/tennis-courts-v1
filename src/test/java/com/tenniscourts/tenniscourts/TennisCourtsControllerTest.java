package com.tenniscourts.tenniscourts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenniscourts.schedules.ScheduleDTO;
import org.junit.Before;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = TennisCourtController.class)
public class TennisCourtsControllerTest {

    @InjectMocks
    private TennisCourtController tennisCourtController;

    @MockBean
    private TennisCourtService tennisCourtService;

    @Autowired
    private MockMvc mvc;

    final static String TENNIS_COURT_API = "/api/tenniscourt";

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should return status code 201 when addTenisCourt method is call")
    public void addTennisCourt() throws Exception {
        CreateTennisCourtRequestDTO tennisCourtRequestDTO = new CreateTennisCourtRequestDTO
                .CreateTennisCourtRequestDTOBuilder().name("Arthur Ashe Stadium").build();

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setStartDateTime(LocalDateTime.now().plusDays(2L));
        scheduleDTO.setEndDateTime(LocalDateTime.now().plusDays(2L).plusHours(1L));
        scheduleDTO.setTennisCourtId(1l);

        String json = new ObjectMapper().writeValueAsString(tennisCourtRequestDTO);

        TennisCourtDTO tennisCourtDTO = new TennisCourtDTO.TennisCourtDTOBuilder()
                .id(1L).name("Arthur Ashe Stadium").tennisCourtSchedules(Arrays.asList(scheduleDTO)).build();

        BDDMockito.given(tennisCourtService.addTennisCourt(Mockito.any(CreateTennisCourtRequestDTO.class))).willReturn(tennisCourtDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(TENNIS_COURT_API.concat("/add"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mvc
                .perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Must return a list of all the TennisCourts and status code 200")
    public void getAll() throws Exception {

        List<TennisCourt> tennisCourtList = new ArrayList<>();

        for(int i = 0; i < 4; i ++){

            TennisCourt tennisCourt = new TennisCourt();
            tennisCourt.setName("Tennis Court ".concat(String.valueOf(i+1)));
            tennisCourt.setId(Long.valueOf(i));
            tennisCourt.setDateCreate(LocalDateTime.now());
            tennisCourt.setDateUpdate(LocalDateTime.now());
            tennisCourt.setIpNumberCreate("1.1.1.1");
            tennisCourt.setIpNumberUpdate("1.1.1.1");
            tennisCourt.setUserUpdate(1L);

            tennisCourtList.add(tennisCourt);
        }

        BDDMockito.given(tennisCourtService.getAllTennisCourts()).willReturn(tennisCourtList);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(TENNIS_COURT_API);

        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].name").value("Tennis Court 1"))
                .andExpect(jsonPath("$[1].name").value("Tennis Court 2"))
                .andExpect(jsonPath("$[2].name").value("Tennis Court 3"))
                .andExpect(jsonPath("$[3].name").value("Tennis Court 4"));
    }

    @Test
    @DisplayName("Should get a tennis court by id")
    public void findById() throws Exception {

        TennisCourtDTO tennisCourtDTO = new TennisCourtDTO.TennisCourtDTOBuilder()
                .id(1L)
                .name("Tennis Court 1")
                .build();

        BDDMockito.given(tennisCourtService.findTennisCourtById(Mockito.anyLong())).willReturn(tennisCourtDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(TENNIS_COURT_API.concat("/id/1"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);


        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("Tennis Court 1"));

    }

}
