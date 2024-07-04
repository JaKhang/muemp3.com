package com.mue;

import com.mue.core.config.StorageProperties;
import com.mue.core.config.WebProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.mue.repositories")
@EntityScan(basePackages = "com.mue.entities")
@SpringBootApplication(scanBasePackages = {"com.mue", "com.mue.core.exception"})
@EnableConfigurationProperties({StorageProperties.class, WebProperties.class})
@EnableJpaAuditing
public class ServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(ServiceMain.class);
    }
}