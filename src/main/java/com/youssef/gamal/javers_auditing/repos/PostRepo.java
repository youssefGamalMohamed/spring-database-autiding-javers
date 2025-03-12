package com.youssef.gamal.javers_auditing.repos;

import org.springframework.data.repository.CrudRepository;

import com.youssef.gamal.javers_auditing.entities.Post;

public interface PostRepo extends CrudRepository<Post,Long> {

}
