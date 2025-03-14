package com.youssef.gamal.javers_auditing.dtos;

import lombok.Builder;

@Builder
public record PostDto(Long id, String title ,String content) {

}
