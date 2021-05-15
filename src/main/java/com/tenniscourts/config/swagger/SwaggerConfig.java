package com.tenniscourts.config.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("com.tenniscourts"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
            .title("Tennis Court Reservation")
            .description("\"Spring Boot REST API for handling the reservation of multiple tennis courts.\"")
            .version("1.0.0")
            .license("Apache License Version 2.0")
            .licenseUrl("\"https://www.apache.org/licenses/LICENSE-2.0\"")
            .build();
    }
}
