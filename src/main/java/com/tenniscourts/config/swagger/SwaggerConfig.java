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
        
    	ApiInfo apiInfo = new ApiInfoBuilder().title("Tennis Courts App")
        									  .description("A simple reservation platform for tennis players.")
        									  .version("1.0")
        									  .build();
		
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo)
        											  .select()
        											  .apis(RequestHandlerSelectors.basePackage("com.tenniscourts"))
        											  .paths(PathSelectors.any())
        											  .build();
    }
}