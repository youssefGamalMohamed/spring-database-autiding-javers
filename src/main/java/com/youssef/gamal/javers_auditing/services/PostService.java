package com.youssef.gamal.javers_auditing.services;

import java.util.List;
import java.util.Optional;

import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.youssef.gamal.javers_auditing.dtos.HistoryDto;
import com.youssef.gamal.javers_auditing.entities.Post;
import com.youssef.gamal.javers_auditing.mappers.HistoryMapper;
import com.youssef.gamal.javers_auditing.mappers.PostMapper;
import com.youssef.gamal.javers_auditing.repos.PostRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepo postRepo;

    private final PostMapper postMapper;

    private final Javers javers;

    private final HistoryMapper historyMapper;

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
        Post existingPost = postRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        // Use the mapper to update the entity
        postMapper.updateFrom(postDetails, existingPost);
        Post updatedPost = postRepo.save(existingPost);
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


    // New method: Get audit history (snapshots) for a Post
    public List<HistoryDto> getPostHistoryDto(Long postId) {
        Post post = postRepo.findById(postId)
                    .orElseThrow(() -> new RuntimeException("Post not found with id " + postId));
        List<CdoSnapshot> snapshots = javers.findSnapshots(QueryBuilder.byInstance(post).build());
        log.info("Retrieved Total Snapshots Size = {}", snapshots.size());
        return (List<HistoryDto>) historyMapper.toHistoryDtos(snapshots);
    }
}