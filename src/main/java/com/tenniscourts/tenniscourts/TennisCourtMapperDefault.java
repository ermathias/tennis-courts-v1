package com.tenniscourts.tenniscourts;

import org.springframework.stereotype.Component;

@Component
public class TennisCourtMapperDefault
	implements
	TennisCourtMapper {

	@Override
	public TennisCourtDTO map(
		final TennisCourt source) {
		return TennisCourtDTO.builder()
				.id(source.getId())
				.name(source.getName())
				.build();
	}

	@Override
	public TennisCourt map(
		final TennisCourtDTO source) {
		TennisCourt result = new TennisCourt();
		result.setId(source.getId());
		result.setName(source.getName());
		return result;
	}
}
