package com.youssef.gamal.javers_auditing.services;

import java.util.Collection;
import java.util.List;

import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.stereotype.Service;

import com.youssef.gamal.javers_auditing.dtos.HistoryDto;
import com.youssef.gamal.javers_auditing.mappers.HistoryMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class JaversHistoryServiceImpl implements HistoryServiceIfc {

    private final Javers javers;
    private final HistoryMapper historyMapper;

    @Override
    public <T> Collection<HistoryDto> getHistoryOf(Class<T> clazz, Long id) {
        log.info("getHistoryOf(class_type = {}, id = {})", clazz.toString(), id);
        List<CdoSnapshot> snapshots = javers.findSnapshots(QueryBuilder.byInstanceId(id, clazz).build());
        log.info("Retrieved Total Snapshots Size = {}", snapshots.size());
        return historyMapper.toHistoryDtos(snapshots);
    }

}
