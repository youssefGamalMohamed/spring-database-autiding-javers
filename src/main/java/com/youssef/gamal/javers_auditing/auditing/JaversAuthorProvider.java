package com.youssef.gamal.javers_auditing.auditing;

import org.javers.spring.auditable.AuthorProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class JaversAuthorProvider implements AuthorProvider {

    @Override
    public String provide() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "UNKNOWN";
        }
        return authentication.getName();
    }

}
