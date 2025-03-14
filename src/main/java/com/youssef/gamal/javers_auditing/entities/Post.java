package com.youssef.gamal.javers_auditing.entities;


import java.time.LocalDateTime;

import org.javers.core.metamodel.annotation.DiffIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Table(name ="POSTS")      
@Entity         
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;


    @CreatedDate
    @Column(updatable = false)
    @DiffIgnore
    private LocalDateTime creationDate;

    @CreatedBy
    @Column(updatable = false)
    @DiffIgnore
    private String createdBy;

    @LastModifiedDate
    @DiffIgnore
    private LocalDateTime lastModifiedDate;


    @LastModifiedBy
    @DiffIgnore
    private String lastModifiedBy;


}
