package com.mue;

import com.mue.core.config.HLSProperties;
import com.mue.core.config.StorageProperties;
import com.mue.core.config.WebProperties;
import com.mue.security.config.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


@EnableJpaRepositories(basePackages = "com.mue.repositories")
@EntityScan(basePackages = "com.mue.entities")
@SpringBootApplication(scanBasePackages = {"com.mue", "com.mue.core.exception"})
@EnableConfigurationProperties({SecurityProperties.class, StorageProperties.class, WebProperties.class, HLSProperties.class})
@EnableJpaAuditing
public class FileProcessorApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FileProcessorApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(FileProcessorApplication.class);
    }
}