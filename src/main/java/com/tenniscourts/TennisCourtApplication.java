package com.tenniscourts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TennisCourtApplication {
    public static void main(String[] args) {
        SpringApplication.run(TennisCourtApplication.class, args);
    }
}