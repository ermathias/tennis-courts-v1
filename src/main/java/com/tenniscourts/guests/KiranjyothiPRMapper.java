package com.tenniscourts.guests;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KiranjyothiPRMapper {

    Guest map(KiranjyothiPRDTO source);

    @InheritInverseConfiguration
    KiranjyothiPRDTO map(Guest source);
}
