package com.tenniscourts.guests;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface GuestMapper {
	
	GuestDTO map(Guest source);

    @InheritInverseConfiguration
    Guest map(GuestDTO source);
    
    Guest map(CreateUpdateGuestRequestDTO source);

}
