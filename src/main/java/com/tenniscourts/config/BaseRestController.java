package com.tenniscourts.config;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.base.Joiner;

import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;

@ApiIgnore
public class BaseRestController {

	protected URI locationByEntity(Long entityId) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entityId).toUri();
	}

	protected void validateInputs(BindingResult bindingResult) {
		if (!bindingResult.getAllErrors().isEmpty())
			throw new IllegalArgumentException(Joiner.on(", ").join(bindingResult.getAllErrors()));
	}

}
