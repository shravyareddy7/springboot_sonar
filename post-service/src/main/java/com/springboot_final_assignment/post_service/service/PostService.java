package com.springboot_final_assignment.post_service.service;

import com.springboot_final_assignment.post_service.entity.Post;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();

    Post getPostById(int id);

    Post createPost(Post post);

    Post updatePost(int id, Post post);

    void deletePost(int id);

    Boolean existsById(int postId);
}