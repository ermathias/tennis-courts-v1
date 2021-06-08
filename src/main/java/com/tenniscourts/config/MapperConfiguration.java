package com.tenniscourts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tenniscourts.guests.GuestMapper;
import com.tenniscourts.guests.GuestMapperImpl;
import com.tenniscourts.reservations.ReservationMapper;
import com.tenniscourts.reservations.ReservationMapperImpl;
import com.tenniscourts.schedules.ScheduleMapper;
import com.tenniscourts.schedules.ScheduleMapperImpl;
import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import com.tenniscourts.tenniscourts.TennisCourtMapper;

@Configuration
public class MapperConfiguration {
	
	private ReservationMapper reservationMapper;
	private ScheduleMapper scheduleMapper;
	private TennisCourtMapper tennisCourtMapper;
	private GuestMapper guestMapper;
	
	@Bean
	public GuestMapper instantiateGuestMapper() {
		guestMapper = new GuestMapperImpl();
		return guestMapper;
	}

	@Bean
	public ReservationMapper instantiateReservationMapper() {
		reservationMapper = new ReservationMapperImpl();
		return reservationMapper;
	}
	
	@Bean
	public ScheduleMapper instantiateScheduleMapper() {
		scheduleMapper = new ScheduleMapperImpl();
		return scheduleMapper;
	}
	
	@Bean
	public TennisCourtMapper instantiateTennisCourtMapper() {
		tennisCourtMapper = new TennisCourtMapper() {
			
			@Override
			public TennisCourt map(TennisCourtDTO source) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public TennisCourtDTO map(TennisCourt source) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		return tennisCourtMapper;
	}

}
