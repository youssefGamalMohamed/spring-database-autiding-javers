package com.youssef.gamal.javers_auditing.services;

import com.youssef.gamal.javers_auditing.entities.Post;
import com.youssef.gamal.javers_auditing.repos.PostRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;

    public Post createPost(Post post) {
        log.info("Starting createPost with post: {}", post);
        Post createdPost = postRepo.save(post);
        log.info("Finished createPost with result: {}", createdPost);
        return createdPost;
    }

    public Optional<Post> getPostById(Long id) {
        log.info("Starting getPostById with id: {}", id);
        Optional<Post> post = postRepo.findById(id);
        log.info("Finished getPostById with result: {}", post);
        return post;
    }

    public Post updatePost(Long id, Post postDetails) {
        log.info("Starting updatePost with id: {} and postDetails: {}", id, postDetails);
        Post updatedPost = postRepo.findById(id).map(post -> {
            post.setTitle(postDetails.getTitle());
            post.setContent(postDetails.getContent());
            return postRepo.save(post);
        }).orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        log.info("Finished updatePost with result: {}", updatedPost);
        return updatedPost;
    }

    public void deletePost(Long id) {
        log.info("Starting deletePost with id: {}", id);
        postRepo.deleteById(id);
        log.info("Finished deletePost with id: {}", id);
    }

    public Page<Post> searchPosts(Pageable pageable) {
        log.info("Starting searchPosts with pageable: {}", pageable);
        Page<Post> posts = postRepo.findAll(pageable);
        log.info("Finished searchPosts with result: {}", posts);
        return posts;
    }
}