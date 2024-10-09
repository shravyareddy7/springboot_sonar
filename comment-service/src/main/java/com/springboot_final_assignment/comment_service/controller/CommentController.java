package com.springboot_final_assignment.comment_service.controller;

import com.springboot_final_assignment.comment_service.entity.Comment;
import com.springboot_final_assignment.comment_service.service.CommentService;
import com.springboot_final_assignment.comment_service.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.COMMENTS_BASE_URL)
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(Constants.GET_ALL_COMMENTS)
    public ResponseEntity<List<Comment>> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping(Constants.GET_COMMENT_BY_ID)
    public ResponseEntity<Comment> getCommentById(@PathVariable int id) {
        return commentService.getCommentById(id);
    }

    @GetMapping(Constants.GET_COMMENTS_BY_POST_ID)
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable int postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @DeleteMapping(Constants.GET_COMMENTS_BY_POST_ID)
    public ResponseEntity<String> deleteCommentsByPostId(@PathVariable int postId) {
        commentService.deleteByPostId(postId);
        return ResponseEntity.ok("Deleted comment");
    }
    @PostMapping(Constants.GET_COMMENTS_BY_POST_ID)
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment,@PathVariable int postId) {
        return commentService.createComment(comment,postId);
    }

    @PutMapping(Constants.UPDATE_COMMENT)
    public ResponseEntity<Comment> updateComment(
            @PathVariable int id,
            @RequestBody Comment comment) {
        return commentService.updateComment(id, comment);
    }

    @DeleteMapping(Constants.DELETE_COMMENT)
    public ResponseEntity<Void> deleteComment(@PathVariable int id) {
        return commentService.deleteComment(id);
    }





}
