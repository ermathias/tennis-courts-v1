package com.tenniscourts.tenniscourts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenniscourts.schedules.ScheduleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TennisCourtControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ScheduleMapper scheduleMapper;

  @Autowired
  private TennisCourtService tennisCourtService;

  @Test
  void shouldAddTennisCourtWhenReceiveAName() throws Exception {

    var tennisCourtDTO = TennisCourtDTO.builder()
            .name( "TesteTennisCourt" )
            .tennisCourtSchedules( new ArrayList<>() )
            .build();

    mockMvc.perform( post( "/tennis-courts" )
            .contentType( "application/json" )
            .content( objectMapper.writeValueAsString( tennisCourtDTO ) ) )
            .andExpect( status().isCreated() );
  }

  @Test
  void shouldFindTennisCourtById() throws Exception {

    var tennisCourtDTO = TennisCourtDTO.builder()
            .name( "TesteTennisCourt" )
            .tennisCourtSchedules( new ArrayList<>() )
            .build();

    mockMvc.perform( post( "/tennis-courts" )
            .contentType( "application/json" )
            .content( objectMapper.writeValueAsString( tennisCourtDTO ) ) );

    var tennisCourt1 = "{\"id\":1,\"name\":\"Roland Garros - Court Philippe-Chatrier\"}";
    var tennisCourt2 = "{\"id\":2,\"name\":\"TesteTennisCourt\"}";

    mockMvc.perform( get( "/tennis-courts/1" ) )
            .andDo( print() )
            .andExpect( status().isOk() )
            .andExpect( content().string( containsString( tennisCourt1 ) ) );

    mockMvc.perform( get( "/tennis-courts/2" ) )
            .andDo( print() )
            .andExpect( status().isOk() )
            .andExpect( content().string( containsString( tennisCourt2 ) ) );
  }

}