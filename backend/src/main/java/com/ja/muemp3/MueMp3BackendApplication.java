package com.ja.muemp3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ConfigurationPropertiesScan("com.ja.muemp3.config.properties")
public class MueMp3BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MueMp3BackendApplication.class, args);
    }

}
