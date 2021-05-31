package com.tenniscourts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class TennisCourtApplication {

    public static void main(String[] args) {
        SpringApplication.run(TennisCourtApplication.class, args);
    }

}