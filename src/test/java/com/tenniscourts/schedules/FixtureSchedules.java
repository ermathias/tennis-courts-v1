package com.tenniscourts.schedules;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class FixtureSchedules {

    public static Schedule fixtureSchedule(){
        return Schedule.builder().startDateTime(LocalDateTime.now()).build();
    }

    public static Schedule fixtureScheduleWithEndTime(){
        var schedule = new Schedule();
        schedule.setStartDateTime(LocalDateTime.now());
        return Schedule.builder()
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now().plusHours(1))
                .build();
    }

    public static Schedule fixtureSchedule(LocalDateTime start){
        var schedule = new Schedule();
        schedule.setStartDateTime(LocalDateTime.now());
        return schedule;
    }

    public static Schedule fixtureScheduleWithId(){
        var schedule = new Schedule();
        schedule.setId(1L);
        schedule.setStartDateTime(LocalDateTime.now());
        return schedule;
    }

    public static ScheduleDTO fixtureScheduleDTO(){
        var scheduleDTO = new ScheduleDTO();
        scheduleDTO.setStartDateTime(LocalDateTime.now());
        return scheduleDTO;
    }

    public static ScheduleDTO fixtureScheduleDTOWithID(LocalDateTime start){
        var scheduleDTO = new ScheduleDTO();
        scheduleDTO.setStartDateTime(start);
        scheduleDTO.setId(1L);

        return scheduleDTO;
    }

    public static ScheduleDTO fixtureScheduleDTOWithID(){
        var scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(1L);

        return scheduleDTO;
    }

    public static List<ScheduleDTO> fixtureScheduleDTOList(){
        var firstSchedule = FixtureSchedules.fixtureScheduleDTO();
        var secondSchedule = FixtureSchedules.fixtureScheduleDTO();
        return Arrays.asList(firstSchedule, secondSchedule);
    }

    public static Schedule fixtureScheduleReservation(Long id){
        Schedule schedule = new Schedule();
        LocalDateTime startDateTime = LocalDateTime.now().plusDays(1);
        schedule.setStartDateTime(startDateTime);
        schedule.setId(id);

        return schedule;
    }

}
