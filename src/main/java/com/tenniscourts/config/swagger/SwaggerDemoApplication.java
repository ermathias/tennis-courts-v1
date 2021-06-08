package com.tenniscourts.config.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
public class SwaggerDemoApplication
{
    public static void main(String[] args) {
        SpringApplication.run(SwaggerDemoApplication.class, args);
    }
}
