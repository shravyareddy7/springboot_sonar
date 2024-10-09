package com.springboot_final_assignment.comment_service.controller;

import com.springboot_final_assignment.comment_service.entity.Comment;
import com.springboot_final_assignment.comment_service.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CommentControllerTest {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1, 1, "Great post!", "User1", 0, new ArrayList<>()));
        comments.add(new Comment(2, 1, "Nice article!", "User2", 0, new ArrayList<>()));

        when(commentService.getAllComments()).thenReturn(new ResponseEntity<>(comments, HttpStatus.OK));

        ResponseEntity<List<Comment>> response = commentController.getAllComments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comments, response.getBody());
        verify(commentService, times(1)).getAllComments();
    }

    @Test
    public void testGetCommentById() {
        Comment comment = new Comment(1, 1, "Great post!", "User1", 0, new ArrayList<>());
        when(commentService.getCommentById(1)).thenReturn(new ResponseEntity<>(comment, HttpStatus.OK));

        ResponseEntity<Comment> response = commentController.getCommentById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comment, response.getBody());
        verify(commentService, times(1)).getCommentById(1);
    }

    @Test
    public void testGetCommentsByPostId() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1, 1, "Great post!", "User1", 0, new ArrayList<>()));
        when(commentService.getCommentsByPostId(1)).thenReturn(new ResponseEntity<>(comments, HttpStatus.OK));

        ResponseEntity<List<Comment>> response = commentController.getCommentsByPostId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comments, response.getBody());
        verify(commentService, times(1)).getCommentsByPostId(1);
    }

    @Test
    public void testCreateComment() {
        Comment comment = new Comment(1, 1, "Great post!", "User1", 0, new ArrayList<>());
        int postId = 1;

        when(commentService.createComment(comment, postId)).thenReturn(new ResponseEntity<>(comment, HttpStatus.CREATED));

        ResponseEntity<Comment> response = commentController.createComment(comment, postId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(comment, response.getBody());
        verify(commentService, times(1)).createComment(comment, postId);
    }

    @Test
    public void testUpdateComment() {
        Comment updatedComment = new Comment(1, 1, "Updated comment!", "User1", 0, new ArrayList<>());
        when(commentService.updateComment(1, updatedComment)).thenReturn(new ResponseEntity<>(updatedComment, HttpStatus.OK));

        ResponseEntity<Comment> response = commentController.updateComment(1, updatedComment);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedComment, response.getBody());
        verify(commentService, times(1)).updateComment(1, updatedComment);
    }

    @Test
    public void testDeleteComment() {
        when(commentService.deleteComment(1)).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        ResponseEntity<Void> response = commentController.deleteComment(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(commentService, times(1)).deleteComment(1);
    }

    @Test
    public void testDeleteCommentsByPostId() {
        int postId = 1;
        ResponseEntity<String> response = commentController.deleteCommentsByPostId(postId);
        verify(commentService, times(1)).deleteByPostId(postId);
        assertEquals(ResponseEntity.ok("Deleted comment"), response);
    }

}
