package com.youssef.gamal.javers_auditing.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(name = "Post")
@Builder
public record PostDto(Long id, String title ,String content) {

}
