package com.tenniscourts.config.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("swagger")
@Getter
@Setter
public class ApiSwaggerProperties {
	private String title;
	private String description;
	private String version;
}
