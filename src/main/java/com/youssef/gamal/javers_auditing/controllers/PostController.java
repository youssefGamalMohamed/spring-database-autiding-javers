package com.youssef.gamal.javers_auditing.controllers;

import com.youssef.gamal.javers_auditing.entities.Post;
import com.youssef.gamal.javers_auditing.services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        log.info("Starting createPost with post: {}", post);
        Post createdPost = postService.createPost(post);
        log.info("Finished createPost with result: {}", createdPost);
        return createdPost;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        log.info("Starting getPostById with id: {}", id);
        Optional<Post> post = postService.getPostById(id);
        log.info("Finished getPostById with result: {}", post);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        log.info("Starting updatePost with id: {} and postDetails: {}", id, postDetails);
        try {
            Post updatedPost = postService.updatePost(id, postDetails);
            log.info("Finished updatePost with result: {}", updatedPost);
            return ResponseEntity.ok(updatedPost);
        } catch (RuntimeException e) {
            log.error("Error in updatePost: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        log.info("Starting deletePost with id: {}", id);
        postService.deletePost(id);
        log.info("Finished deletePost with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public Page<Post> searchPosts(Pageable pageable) {
        log.info("Starting searchPosts with pageable: {}", pageable);
        Page<Post> posts = postService.searchPosts(pageable);
        log.info("Finished searchPosts with result: {}", posts);
        return posts;
    }
}