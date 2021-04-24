package com.tenniscourts.tenniscourts;

import com.tenniscourts.schedules.FixtureSchedules;
import com.tenniscourts.schedules.ScheduleDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FixtureTennisCourt {

    public static TennisCourt fixtureTennisCourt(){
        var tennisCourt = new TennisCourt();
        tennisCourt.setName("Roland Garros");
        tennisCourt.setId(1L);

        return tennisCourt;
    }

    public static List<TennisCourt> fixtureTennisCourtList(){
        var firstTennisCourt = new TennisCourt();
        firstTennisCourt.setName("Roland Garros");
        firstTennisCourt.setId(1L);

        return Arrays.asList(firstTennisCourt);
    }

    public static TennisCourtDTO fixtureTennisCourtDTO(){
        var tennisCourtDTO = new TennisCourtDTO();
        tennisCourtDTO.setName("Roland Garros");
        tennisCourtDTO.setId(1L);

        return tennisCourtDTO;
    }

    public static List<TennisCourtDTO> fixtureTennisCourtDTOList(List<ScheduleDTO> scheduleDTOList){
        var firstTennisCourtDTO = new TennisCourtDTO();
        firstTennisCourtDTO.setName("Roland Garros");
        firstTennisCourtDTO.setId(1L);
        firstTennisCourtDTO.setTennisCourtSchedules(scheduleDTOList);

        var tennisCourtDTOS = new ArrayList<TennisCourtDTO>();
        tennisCourtDTOS.add(firstTennisCourtDTO);
        return tennisCourtDTOS;
    }

    public static TennisCourtDTO fixtureTennisCourtDTOWithSchedule(){
        var scheduleDTOList = FixtureSchedules.fixtureScheduleDTOList();

        var tennisCourtDTO = new TennisCourtDTO();
        tennisCourtDTO.setName("Roland Garros");
        tennisCourtDTO.setId(1L);
        tennisCourtDTO.setTennisCourtSchedules(scheduleDTOList);

        return tennisCourtDTO;
    }
}
