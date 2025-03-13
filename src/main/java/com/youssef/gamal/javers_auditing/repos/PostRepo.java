package com.youssef.gamal.javers_auditing.repos;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.youssef.gamal.javers_auditing.entities.Post;


@JaversSpringDataAuditable
@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

}
