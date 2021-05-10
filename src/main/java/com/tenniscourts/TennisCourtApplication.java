package com.tenniscourts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = {"com"})
@ComponentScan(basePackages = {"annotations.*"})
//@SpringBootApplication
@EnableJpaAuditing
public class TennisCourtApplication {

    public static void main(String[] args) {
        SpringApplication.run(TennisCourtApplication.class, args);
    }

}