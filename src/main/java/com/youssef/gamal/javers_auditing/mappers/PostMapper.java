package com.youssef.gamal.javers_auditing.mappers;

import org.mapstruct.Mapper;

import com.youssef.gamal.javers_auditing.entities.Post;
import com.youssef.gamal.javers_auditing.dtos.PostDto;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    // Map Post entity to PostDto; only id, title, and content are mapped.
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "content", source = "content")
    PostDto toDto(Post post);

    // Map PostDto back to Post entity. 
    // Note: Auditing fields are ignored here. They are normally managed by Spring Data JPA.
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    Post toEntity(PostDto postDto);
}
