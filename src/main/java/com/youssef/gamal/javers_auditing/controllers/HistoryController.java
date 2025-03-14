package com.youssef.gamal.javers_auditing.controllers;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.youssef.gamal.javers_auditing.dtos.HistoryDto;
import com.youssef.gamal.javers_auditing.services.HistoryServiceIfc;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@AllArgsConstructor
public class HistoryController<E> {

    private final HistoryServiceIfc historyServiceIfc;

    private Class<E> clazz;

    @GetMapping("/{id}/histories")
    public Collection<HistoryDto> getHistoryByEntityId(@PathVariable Long id) {
        log.info("Started Geting Histor for Entity");
        Collection<HistoryDto> history = historyServiceIfc.getHistoryOf(clazz, id);
        log.info("History of the Entity is with size = {}", history.size());
        return history;
    }
}
