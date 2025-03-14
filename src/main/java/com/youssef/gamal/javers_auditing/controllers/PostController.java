package com.youssef.gamal.javers_auditing.controllers;
import java.util.List;
import java.util.Optional;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youssef.gamal.javers_auditing.dtos.HistoryDto;
import com.youssef.gamal.javers_auditing.dtos.PostDto;
import com.youssef.gamal.javers_auditing.entities.Post;
import com.youssef.gamal.javers_auditing.mappers.PostMapper;
import com.youssef.gamal.javers_auditing.services.PostService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @PostMapping
    public PostDto createPost(@RequestBody PostDto postDto) {
        log.info("Starting createPost with postDto: {}", postDto);
        // Convert DTO to entity
        Post postEntity = postMapper.toEntity(postDto);
        // Call service layer to persist the entity
        Post createdPost = postService.createPost(postEntity);
        log.info("Finished createPost with result: {}", createdPost);
        // Convert the entity back to DTO for the response
        return postMapper.toDto(createdPost);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        log.info("Starting getPostById with id: {}", id);
        Optional<Post> postOpt = postService.getPostById(id);
        log.info("Finished getPostById with result: {}", postOpt);
        return postOpt
                .map(post -> ResponseEntity.ok(postMapper.toDto(post)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody PostDto postDto) {
        log.info("Starting updatePost with id: {} and postDto: {}", id, postDto);
        try {
            Post postEntity = postMapper.toEntity(postDto);
            Post updatedPost = postService.updatePost(id, postEntity);
            log.info("Finished updatePost with result: {}", updatedPost);
            return ResponseEntity.ok(postMapper.toDto(updatedPost));
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

    @GetMapping("/")
    public Page<PostDto> searchPosts(@ParameterObject Pageable pageable) {
        log.info("Starting searchPosts with pageable: {}", pageable);
        Page<Post> posts = postService.searchPosts(pageable);
        // Map each Post entity to a PostDto
        Page<PostDto> postDtos = posts.map(postMapper::toDto);
        log.info("Finished searchPosts with result: {}", postDtos);
        return postDtos;
    }


    // New endpoint: Get audit history for a Post
    @GetMapping("/{id}/history")
    public ResponseEntity<List<HistoryDto>> getPostHistory(@PathVariable Long id) {
        log.info("Starting getPostHistory for post id: {}", id);
        try {
            List<HistoryDto> snapshots = postService.getPostHistoryDto(id);
            log.info("Finished getPostHistory with {} snapshots", snapshots.size());
            return ResponseEntity.ok(snapshots);
        } catch (RuntimeException e) {
            log.error("Error retrieving history for post id {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}