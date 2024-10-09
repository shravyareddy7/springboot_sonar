package com.springboot_final_assignment.comment_service.service;

import com.springboot_final_assignment.comment_service.entity.Comment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    ResponseEntity<List<Comment>> getAllComments();

    ResponseEntity<Comment> getCommentById(int id);

    ResponseEntity<List<Comment>> getCommentsByPostId(int postId);

    ResponseEntity<Comment> createComment(Comment comment,int postId);

    ResponseEntity<Comment> updateComment(int id, Comment comment);

    ResponseEntity<Void> deleteComment(int id);

    ResponseEntity<Void> deleteByPostId(int postId);
}
