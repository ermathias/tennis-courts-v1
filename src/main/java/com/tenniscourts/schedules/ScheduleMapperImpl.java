package com.tenniscourts.schedules;

import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-19T19:59:16+0530",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_191 (Oracle Corporation)"
)
@Component
public class ScheduleMapperImpl implements ScheduleMapper {

    @Override
    public Schedule map(ScheduleDTO source) {
        if ( source == null ) {
            return null;
        }

        Schedule schedule = new Schedule();

        schedule.setId( source.getId() );
        schedule.setTennisCourt( tennisCourtDTOToTennisCourt( source.getTennisCourt() ) );
        schedule.setStartDateTime( source.getStartDateTime() );
        schedule.setEndDateTime( source.getEndDateTime() );

        return schedule;
    }

    @Override
    public ScheduleDTO map(Schedule source) {
        if ( source == null ) {
            return null;
        }

        ScheduleDTO scheduleDTO = new ScheduleDTO();

        scheduleDTO.setId( source.getId() );
        scheduleDTO.setTennisCourt( tennisCourtToTennisCourtDTO( source.getTennisCourt() ) );
        scheduleDTO.setStartDateTime( source.getStartDateTime() );
        scheduleDTO.setEndDateTime( source.getEndDateTime() );

        return scheduleDTO;
    }

    @Override
    public List<ScheduleDTO> map(List<Schedule> source) {
        if ( source == null ) {
            return null;
        }

        List<ScheduleDTO> list = new ArrayList<ScheduleDTO>( source.size() );
        for ( Schedule schedule : source ) {
            list.add( map( schedule ) );
        }

        return list;
    }

    protected TennisCourt tennisCourtDTOToTennisCourt(TennisCourtDTO tennisCourtDTO) {
        if ( tennisCourtDTO == null ) {
            return null;
        }

        TennisCourt tennisCourt = new TennisCourt();

        tennisCourt.setId( tennisCourtDTO.getId() );
        tennisCourt.setName( tennisCourtDTO.getName() );

        return tennisCourt;
    }

    protected TennisCourtDTO tennisCourtToTennisCourtDTO(TennisCourt tennisCourt) {
        if ( tennisCourt == null ) {
            return null;
        }

        TennisCourtDTO tennisCourtDTO = new TennisCourtDTO();

        tennisCourtDTO.setId( tennisCourt.getId() );
        tennisCourtDTO.setName( tennisCourt.getName() );

        return tennisCourtDTO;
    }
}
