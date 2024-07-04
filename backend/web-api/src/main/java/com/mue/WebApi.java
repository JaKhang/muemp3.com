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

@EnableJpaRepositories(basePackages = "com.mue.repositories")
@EntityScan(basePackages = "com.mue.entities")
@SpringBootApplication(scanBasePackages = {"com.mue"})
@EnableConfigurationProperties({SecurityProperties.class, StorageProperties.class, WebProperties.class, HLSProperties.class})
@EnableJpaAuditing
public class WebApi extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        return application.sources(WebApi.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WebApi.class);
    }
}
