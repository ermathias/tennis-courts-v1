package com.tenniscourts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tenniscourts.guests.GuestMapper;
import com.tenniscourts.guests.GuestMapperImpl;
import com.tenniscourts.reservations.ReservationMapper;
import com.tenniscourts.reservations.ReservationMapperImpl;
import com.tenniscourts.schedules.ScheduleMapper;
import com.tenniscourts.schedules.ScheduleMapperImpl;
import com.tenniscourts.tenniscourts.TennisCourtMapper;
import com.tenniscourts.tenniscourts.TennisCourtMapperImpl;

//Configuration class meant to provida Mapper instances required by the application
@Configuration
public class MapperConfiguration {
	
	@Bean
	public GuestMapper instantiateGuestMapper() {
		return new GuestMapperImpl();
	}

	@Bean
	public ReservationMapper instantiateReservationMapper() {
		return new ReservationMapperImpl();
	}
	
	@Bean
	public ScheduleMapper instantiateScheduleMapper() {
		return new ScheduleMapperImpl();
	}
	
	@Bean
	public TennisCourtMapper instantiateTennisCourtMapper() {
		return new TennisCourtMapperImpl();
	}

}
