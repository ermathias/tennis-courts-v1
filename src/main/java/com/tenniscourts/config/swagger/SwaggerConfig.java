package com.tenniscourts.config.swagger;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableConfigurationProperties({ ApiSwaggerProperties.class })
public class SwaggerConfig {

	public static final String TENNIS_COURTS_VALUE = "Tennis Courts Endpoints";
	public static final String TENNIS_COURTS_ENDPOINT = "tennis-courts-endpoints";
	public static final String RESERVATION_VALUE = "Reservation Endpoints";
	public static final String RESERVATION_ENDPOINT = "reservation-endpoints";
	public static final String SCHEDULER_VALUE = "Scheduler Endpoints";
	public static final String SCHEDULER_ENDPOINT = "schedule-endpoints";
	public static final String GUEST_VALUE = "Guest Endpoints";
	public static final String GUEST_ENDPOINT = "guest-endpoints";
	public static final int OK_CODE = 200;
	public static final String OK_MESSAGE = "OK";
	public static final int CREATED_CODE = 201;
	public static final String CREATED_MESSAGE = "CREATED";
	public static final int INTERNAL_SERVER_ERROR_CODE = 500;
	public static final String INTERNAL_SERVER_ERROR_MESSAGE = "INTERNAL SERVER ERROR";
	public static final int BAD_REQUEST_CODE = 400;
	public static final String BAD_REQUEST_MESSAGE = "BAD REQUEST";
	public static final int NOT_FOUND_CODE = 200;
	public static final String NOT_FOUND_MESSAGE = "NOT FOUND";

	@Bean
	public Docket productApi(final ApiSwaggerProperties apiSwaggerProperties) {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.tenniscourts")).paths(PathSelectors.any()).build()
				.apiInfo(metaData(apiSwaggerProperties))
				.tags(new Tag(TENNIS_COURTS_ENDPOINT, "Tennis Courts Endpoints"),
						new Tag(RESERVATION_ENDPOINT, "Reservation Endpoints"),
						new Tag(SCHEDULER_ENDPOINT, "Schedule Endpoints"), new Tag(GUEST_ENDPOINT, "Guest Endpoints"));
	}

	private ApiInfo metaData(final ApiSwaggerProperties apiSwaggerProperties) {
		return new ApiInfoBuilder().title(apiSwaggerProperties.getTitle())
				.description(apiSwaggerProperties.getDescription()).version(apiSwaggerProperties.getVersion()).build();
	}
}
