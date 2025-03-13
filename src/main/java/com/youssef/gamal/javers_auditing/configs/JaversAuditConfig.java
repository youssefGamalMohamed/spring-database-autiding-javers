package com.youssef.gamal.javers_auditing.configs;

import java.util.Map;

import org.javers.spring.auditable.AuthorProvider;
import org.javers.spring.auditable.CommitPropertiesProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.youssef.gamal.javers_auditing.auditing.JaversAuthorProvider;

@Configuration
public class JaversAuditConfig {

    // Enable this if your project contains spring security, username will be auto added.
    @Bean
    AuthorProvider authorProvider(){
        return new JaversAuthorProvider();
    }

    
    // Used When u want to add external Data as Properties to each commit as an example Tenant name
    @Bean
    CommitPropertiesProvider commitPropertiesProvider(){
        return new CommitPropertiesProvider() {
            @Override
            public Map<String, String> provide() {
                return Map.of("tenant","tenant-1"); // as an example if i use multi-tenant
            }
        };
    }

}
