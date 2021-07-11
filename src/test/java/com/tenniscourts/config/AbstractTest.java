package com.tenniscourts.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tenniscourts.dto.CreateReservationRequestDTO;
import com.tenniscourts.dto.CreateScheduleRequestDTO;
import com.tenniscourts.dto.TennisCourtDTO;

import java.time.LocalDateTime;

public class AbstractTest {

    private ObjectMapper createObjectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return objectMapper;
    }

    public String createScheduleRequestJson() throws Exception {

        CreateScheduleRequestDTO createScheduleRequestDTO = new CreateScheduleRequestDTO();
        createScheduleRequestDTO.setTennisCourtId(1L);
        createScheduleRequestDTO.setStartDateTime(LocalDateTime.now().plusDays(2));
        ObjectWriter objectWriter = createObjectMapper().writer().withDefaultPrettyPrinter();
        String createScheduleRequestJson = objectWriter.writeValueAsString(createScheduleRequestDTO);
        System.out.println(createScheduleRequestJson);
        return createScheduleRequestJson;

    }

    public String createReservationRequestJson() throws Exception {

        CreateReservationRequestDTO createReservationRequestDTO = new CreateReservationRequestDTO();
        createReservationRequestDTO.setGuestId(1L);
        createReservationRequestDTO.setScheduleId(1L);
        ObjectWriter objectWriter = createObjectMapper().writer().withDefaultPrettyPrinter();
        String createReservationRequestJson = objectWriter.writeValueAsString(createReservationRequestDTO);
        System.out.println(createReservationRequestJson);
        return createReservationRequestJson;

    }

    public String createTennisCourtRequestJson() throws Exception {

        TennisCourtDTO tennisCourtDTO = new TennisCourtDTO();
        tennisCourtDTO.setId(1L);
        tennisCourtDTO.setName("A1");
        ObjectWriter objectWriter = createObjectMapper().writer().withDefaultPrettyPrinter();
        String createTennisCourtRequestJson = objectWriter.writeValueAsString(tennisCourtDTO);
        System.out.println(createTennisCourtRequestJson);
        return createTennisCourtRequestJson;

    }
}
