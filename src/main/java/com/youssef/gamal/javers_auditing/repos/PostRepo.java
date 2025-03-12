package com.youssef.gamal.javers_auditing.repos;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.youssef.gamal.javers_auditing.entities.Post;

public interface PostRepo extends ListCrudRepository<Post,Long> 
    , PagingAndSortingRepository<Post,Long> {

}
