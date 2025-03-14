package com.youssef.gamal.javers_auditing.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.youssef.gamal.javers_auditing.auditing.SecurityAuditorAware;

@Configuration
@EnableJpaAuditing
public class JpaConfig {


    @Bean
    AuditorAware<String> auditorProvider() {
        return new SecurityAuditorAware();
    }
    
}
