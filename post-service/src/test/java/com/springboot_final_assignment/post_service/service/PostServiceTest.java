package com.springboot_final_assignment.post_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.springboot_final_assignment.post_service.client.CommentClient;
import com.springboot_final_assignment.post_service.dto.Comment;
import com.springboot_final_assignment.post_service.entity.Post;
import com.springboot_final_assignment.post_service.exception.PostNotFoundException;
import com.springboot_final_assignment.post_service.exception.PostsNotFoundException;
import com.springboot_final_assignment.post_service.repository.PostRepository;

class PostServiceTest {

    @Mock
    private PostRepository repository;

    @Mock
    private CommentClient commentClient;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        postService = new PostServiceImpl(commentClient, repository);
    }

    @Test
    void testGetAllPosts_WithCommentClientFailure() {
        Post post1 = new Post("url1", "caption1", 10, 5);
        List<Post> posts = Collections.singletonList(post1);

        when(repository.findAll()).thenReturn(posts);
        when(commentClient.getCommentsByPostId(post1.getId())).thenThrow(new RuntimeException("Failed to fetch comments"));

        List<Post> result = postService.getAllPosts();

        assertTrue(result.get(0).getComments().isEmpty());
        verify(commentClient, times(1)).getCommentsByPostId(post1.getId());
    }
    @Test
    void testGetAllPosts_WithSuccessfulCommentFetch() {
        Post post1 = new Post("url1", "caption1", 10, 5);
        post1.setId(1);
        List<Post> posts = Collections.singletonList(post1);

        Comment comment1 = new Comment(1, post1.getId(), "Great post!", "User1", 10, new ArrayList<>());
        List<Comment> comments = Collections.singletonList(comment1);

        when(repository.findAll()).thenReturn(posts);

        when(commentClient.getCommentsByPostId(post1.getId())).thenReturn(comments);

        List<Post> result = postService.getAllPosts();

        assertFalse(result.get(0).getComments().isEmpty());
        assertEquals(1, result.get(0).getComments().size());
        assertEquals(comment1, result.get(0).getComments().get(0));

        verify(commentClient, times(1)).getCommentsByPostId(post1.getId());
    }



    @Test
    void testGetAllPostsWhenEmpty() {
        when(repository.findAll()).thenReturn(Arrays.asList());

        assertThrows(PostNotFoundException.class, () -> postService.getAllPosts());
    }

    @Test
    void testGetPostById() {
        Post post = new Post("url", "caption", 10, 5);
        when(repository.findById(1)).thenReturn(Optional.of(post));
        when(commentClient.getCommentsByPostId(1)).thenReturn(Arrays.asList(new Comment()));

        Post result = postService.getPostById(1);

        assertNotNull(result);
        assertEquals("url", result.getPictureUrl());
        assertEquals("caption", result.getCaption());
        assertEquals(10, result.getLikes());
        assertEquals(5, result.getShares());
        assertNotNull(result.getComments());
        assertFalse(result.getComments().isEmpty());
        verify(repository, times(1)).findById(1);
        verify(commentClient, times(1)).getCommentsByPostId(1);
    }
    @Test
    void testGetPostByIdNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.getPostById(1));
    }

    @Test
    void testCreatePost() {
        Post post = new Post("url", "caption", 10, 5);

        when(repository.save(any(Post.class))).thenAnswer(invocation -> {
            Post savedPost = invocation.getArgument(0);
            savedPost.setId(1);
            return savedPost;
        });

        Post result = postService.createPost(post);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(repository, times(1)).save(post);
    }


    @Test
    void testUpdatePost() {
        Post existingPost = new Post( "oldUrl", "oldCaption", 10, 5);
        Post updatedPost = new Post("newUrl", "newCaption", 20, 15);
        when(repository.findById(1)).thenReturn(Optional.of(existingPost));
        when(repository.save(any(Post.class))).thenReturn(updatedPost);

        Post result = postService.updatePost(1, updatedPost);
        assertNotNull(result);
        assertEquals("newUrl", result.getPictureUrl());
        assertEquals("newCaption", result.getCaption());
        assertEquals(20, result.getLikes());
        assertEquals(15, result.getShares());
        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).save(any(Post.class));
    }

    private void updateNonExistentPost() {
        postService.updatePost(1, new Post());
    }
    @Test
    void testUpdatePostNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(PostsNotFoundException.class, this::updateNonExistentPost);
    }



    @Test
    void testDeletePost() {
        when(repository.findById(1)).thenReturn(Optional.of(new Post()));
        postService.deletePost(1);

        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void testDeletePostNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.deletePost(1));
    }

    @Test
    void testExistsById() {
        when(repository.findById(1)).thenReturn(Optional.of(new Post()));
        when(repository.findById(2)).thenReturn(Optional.empty());

        assertTrue(postService.existsById(1));
        assertFalse(postService.existsById(2));
    }

    @Test
     void testGetPostById_FailFeignException() {
        int postId = 1;
        Post post = new Post();
        post.setId(postId);
        Optional<Post> optionalPost = Optional.of(post);

        when(repository.findById(postId)).thenReturn(optionalPost);
        FeignException feignException = mock(FeignException.class);
        when(feignException.status()).thenReturn(500);
        when(commentClient.getCommentsByPostId(postId)).thenThrow(feignException);

        FeignException thrownException = assertThrows(FeignException.class, () -> postService.getPostById(postId));

        assertEquals(500, thrownException.status());
        verify(commentClient, times(1)).getCommentsByPostId(postId);
    }

    @Test
     void testGetPostById_CommentClient404Exception() {
        int postId = 1;
        Post post = new Post();
        post.setId(postId);
        Optional<Post> optionalPost = Optional.of(post);

        when(repository.findById(postId)).thenReturn(optionalPost);
        FeignException feignException = mock(FeignException.class);
        when(feignException.status()).thenReturn(404);
        when(commentClient.getCommentsByPostId(postId)).thenThrow(feignException);

        Post result = postService.getPostById(postId);

        assertNotNull(result);
        assertEquals(postId, result.getId());
        assertEquals(0, result.getComments().size());
        verify(commentClient, times(1)).getCommentsByPostId(postId);
    }

}