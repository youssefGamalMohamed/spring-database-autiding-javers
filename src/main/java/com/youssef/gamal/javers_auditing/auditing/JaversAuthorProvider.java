package com.youssef.gamal.javers_auditing.auditing;

import org.javers.spring.auditable.AuthorProvider;

public class JaversAuthorProvider implements AuthorProvider {

    @Override
    public String provide() {
        return "Youssef Gamal";
    }

}
