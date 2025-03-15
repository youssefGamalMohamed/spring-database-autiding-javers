package com.youssef.gamal.javers_auditing.mappers;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.core.metamodel.object.CdoSnapshotState;
import org.javers.core.metamodel.object.SnapshotType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.youssef.gamal.javers_auditing.dtos.HistoryDto;

@Mapper(componentModel = "spring")
public interface HistoryMapper {


    @Mapping(target = "version", source = "version")
    @Mapping(target = "changedFieldsNames", source = "changed")
    @Mapping(target = "state", expression = "java(convertStateToMap(javerSnapShot.getState()))")
    @Mapping(target = "operationType" , expression = "java(convertOperationTypeToReadableName(javerSnapShot.getType()))")
    HistoryDto toHistoryDto(CdoSnapshot javerSnapShot);


    Collection<HistoryDto> toHistoryDtos(Collection<CdoSnapshot> javerSnapShots);
    
    default Map<String, Object> convertStateToMap(CdoSnapshotState state) {
        if (state == null) {
            return Map.of();
        }
        return state.getPropertyNames().stream()
                .collect(Collectors.toMap(
                    propertyName -> propertyName,
                    propertyName -> state.getPropertyValue(propertyName)
                ));
    }


    default String convertOperationTypeToReadableName(SnapshotType type) {
        if(type.equals(SnapshotType.INITIAL))
            return "CREATED";
        if(type.equals(SnapshotType.UPDATE))
            return "UPDATED";
        if(type.equals(SnapshotType.TERMINAL))
            return "DELETED";
        throw new IllegalArgumentException("Javer Type Is Not Supported");
    }
}
