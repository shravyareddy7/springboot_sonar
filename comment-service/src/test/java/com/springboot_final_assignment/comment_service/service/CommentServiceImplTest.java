package com.springboot_final_assignment.comment_service.service;

import com.springboot_final_assignment.comment_service.client.PostClient;
import com.springboot_final_assignment.comment_service.entity.Comment;
import com.springboot_final_assignment.comment_service.exception.CommentNotFoundException;
import com.springboot_final_assignment.comment_service.exception.CommentsNotFoundException;
import com.springboot_final_assignment.comment_service.exception.PostNotFoundException;
import com.springboot_final_assignment.comment_service.repository.CommentRepository;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostClient postClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testGetAllComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1, 1, "Great post!", "User1", 0, new ArrayList<>()));

        when(commentRepository.findAll()).thenReturn(comments);

        ResponseEntity<List<Comment>> response = commentService.getAllComments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Great post!", response.getBody().get(0).getComment());
    }

    @Test
     void testGetAllComments_NoComments() {
        when(commentRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(CommentsNotFoundException.class, () -> {
            commentService.getAllComments();
        });
    }

    @Test
     void testGetCommentById() {
        Comment comment = new Comment(1, 1, "Great post!", "User1", 0, new ArrayList<>());
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));

        ResponseEntity<Comment> response = commentService.getCommentById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Great post!", response.getBody().getComment());
    }

    @Test
     void testGetCommentById_NotFound() {
        when(commentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            commentService.getCommentById(1);
        });
    }


    @Test
     void testCreateComment() {
        Comment comment = new Comment(0, 1, "Great post!", "User1", 0, new ArrayList<>());
  when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        ResponseEntity<Comment> response = commentService.createComment(comment,1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Great post!", response.getBody().getComment());
    }

    @Test
     void testCreateComment_Success() {
        Comment comment = new Comment(0, 1, "Great post!", "User1", 0, new ArrayList<>());
        when(commentRepository.save(comment)).thenReturn(comment);

        ResponseEntity<Comment> response = commentService.createComment(comment,1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comment, response.getBody());
    }


    @Test
     void testUpdateComment() {
        Comment existingComment = new Comment(1, 1, "Old comment", "User1", 0, new ArrayList<>());
        Comment updatedComment = new Comment(1, 1, "Updated comment", "User1", 0, new ArrayList<>());

        when(commentRepository.findById(1)).thenReturn(Optional.of(existingComment));
        when(commentRepository.save(any(Comment.class))).thenReturn(updatedComment);

        ResponseEntity<Comment> response = commentService.updateComment(1, updatedComment);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated comment", response.getBody().getComment());
    }

    @Test
     void testUpdateComment_NotFound() {
        Comment updatedComment = new Comment(1, 1, "Updated comment", "User1", 0, new ArrayList<>());

        when(commentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () -> {
            commentService.updateComment(1, updatedComment);
        });
    }

    @Test
     void testDeleteComment() {
        Comment comment = new Comment(1, 1, "Great post!", "User1", 0, new ArrayList<>());

        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));

        ResponseEntity<Void> response = commentService.deleteComment(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(commentRepository, times(1)).deleteById(1);
    }

    @Test
     void testDeleteComment_NotFound() {
        when(commentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () -> {
            commentService.deleteComment(1);
        });
    }

    @Test
     void testCreateComment_PostNotFound() {
        Comment comment = new Comment(0, 1, "Great post!", "User1", 0, new ArrayList<>());

        Request request = Request.create(
                Request.HttpMethod.GET,
                "http://example.com",
                Collections.emptyMap(),
                null,
                StandardCharsets.UTF_8,
                new RequestTemplate()
        );


        doThrow(new FeignException.NotFound("Post not found", request, null,null))
                .when(postClient)
                .checkIfPostExists(comment.getPostId());

        assertThrows(PostNotFoundException.class, () -> {
            commentService.createComment(comment,1);
        });
        verify(postClient).checkIfPostExists(comment.getPostId());
    }

    @Test
     void testDeleteCommentsByPostId() {
        int postId = 1;

        doNothing().when(commentRepository).deleteByPostId(postId);

        ResponseEntity<Void> response = commentService.deleteByPostId(postId);

        verify(commentRepository, times(1)).deleteByPostId(postId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetCommentsByPostId_PostExists_CommentsFound() {
        int postId = 1;
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1, postId, "Great post!", "User1", 0, new ArrayList<>()));

        when(postClient.checkIfPostExists(postId)).thenReturn(true);
        when(commentRepository.findByPostId(postId)).thenReturn(comments);

        ResponseEntity<List<Comment>> response = commentService.getCommentsByPostId(postId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }
    @Test
    void testGetCommentsByPostId_PostExists_NoComments() {
        int postId = 1;
        when(postClient.checkIfPostExists(postId)).thenReturn(true);
        when(commentRepository.findByPostId(postId)).thenReturn(new ArrayList<>());
        assertThrows(CommentsNotFoundException.class, () -> {
            commentService.getCommentsByPostId(postId);
        });
    }
    @Test
    void testGetCommentsByPostId_PostDoesNotExist() {
        int postId = 1;
        when(postClient.checkIfPostExists(postId)).thenReturn(false);
        assertThrows(PostNotFoundException.class, () -> {
            commentService.getCommentsByPostId(postId);
        });
    }

@Test
void testGetCommentsByPostId_PostCheckThrowsException() {
    int postId = 1;

    Request mockRequest = Request.create(
            Request.HttpMethod.GET,
            "http://localhost:8081/posts/" + postId,
            Collections.emptyMap(),
            null,
            null,
            null
    );

    FeignException.NotFound notFoundException = new FeignException.NotFound(
            "Post not found",
            mockRequest,
            null,
            null
    );

    when(postClient.checkIfPostExists(postId)).thenThrow(notFoundException);
    assertThrows(PostNotFoundException.class, () -> {
        commentService.getCommentsByPostId(postId);
    });
}
}
