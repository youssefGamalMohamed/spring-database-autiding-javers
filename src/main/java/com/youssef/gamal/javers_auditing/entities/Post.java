package com.youssef.gamal.javers_auditing.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Table("POSTS")
public class Post {

    @Id
    private Long id;

    private String title;
    private String content;
    
}
