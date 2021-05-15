package com.tenniscourts.tenniscourts;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TennisCourtMapper {

	public static final TennisCourtMapper TENNIS_COURT_MAPPER_INSTANCE = Mappers.getMapper(TennisCourtMapper.class);

	TennisCourtDTO map(TennisCourt source);

	TennisCourt map(TennisCourtRequest tennisCourtRequest);

	@InheritInverseConfiguration
	TennisCourt map(TennisCourtDTO source);
}
