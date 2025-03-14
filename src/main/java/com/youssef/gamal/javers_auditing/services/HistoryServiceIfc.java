package com.youssef.gamal.javers_auditing.services;

import java.util.Collection;

import com.youssef.gamal.javers_auditing.dtos.HistoryDto;

public interface HistoryServiceIfc {
    
    <T> Collection<HistoryDto> getHistoryOf(Class<T> clazz, Long id);
}
