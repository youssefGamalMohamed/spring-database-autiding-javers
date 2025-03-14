package com.youssef.gamal.javers_auditing.dtos;

import java.util.List;
import java.util.Map;

import lombok.Builder;

@Builder
public record HistoryDto(Long version, List<String> changedFieldsNames, Map<String,Object> state) {
}
