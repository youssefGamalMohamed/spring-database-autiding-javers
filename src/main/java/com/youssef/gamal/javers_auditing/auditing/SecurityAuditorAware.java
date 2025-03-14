package com.youssef.gamal.javers_auditing.auditing;


import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.AllArgsConstructor;

import org.springframework.security.core.Authentication;

@AllArgsConstructor
public class SecurityAuditorAware implements AuditorAware<String> {
    
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of("UNKNOWN");
        }
        return Optional.of(authentication.getName());
    }

}
