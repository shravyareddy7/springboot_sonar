package com.springboot_final_assignment.post_service.controller;

import com.springboot_final_assignment.post_service.client.CommentClient;
import com.springboot_final_assignment.post_service.entity.Post;
import com.springboot_final_assignment.post_service.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @Mock
    private PostService postService;

    @Mock
    private CommentClient commentClient;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPosts() {
        Post post1 = new Post("url1", "caption1", 10, 5);
        post1.setId(1);
        List<Post> posts = Collections.singletonList(post1);
        when(postService.getAllPosts()).thenReturn(posts);
        List<Post> result = postController.getAllPosts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("caption1", result.get(0).getCaption());
        verify(postService, times(1)).getAllPosts();
    }

    @Test
    void testGetPostById() {
        Post post = new Post("url2", "caption2", 20, 10);
        post.setId(2);
        when(postService.getPostById(2)).thenReturn(post);
        Post result = postController.getPost(2);

        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals("caption2", result.getCaption());
        verify(postService, times(1)).getPostById(2);
    }

    @Test
    void testCreatePost() {
        Post post = new Post("url3", "caption3", 30, 15);
        postController.createPost(post);

        verify(postService, times(1)).createPost(any(Post.class));
    }

    @Test
    void testUpdatePost() {
        Post post = new Post("url4", "caption4", 40, 20);
        post.setId(4);
        postController.updatePost(4, post);

        verify(postService, times(1)).updatePost(eq(4), any(Post.class));
    }

    @Test
    void testDeletePost() {
        postController.deletePost(5);
        verify(postService, times(1)).deletePost(5);
    }

    @Test
    void testCheckIfPostExists() {
        when(postService.existsById(6)).thenReturn(true);
        Boolean result = postController.checkIfPostExists(6);
        assertTrue(result);
        verify(postService, times(1)).existsById(6); // Verify method call
    }
}
