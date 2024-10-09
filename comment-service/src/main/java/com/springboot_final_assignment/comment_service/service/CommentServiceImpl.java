package com.springboot_final_assignment.comment_service.service;

import com.springboot_final_assignment.comment_service.client.PostClient;
import com.springboot_final_assignment.comment_service.dto.Post;
import com.springboot_final_assignment.comment_service.entity.Comment;
import com.springboot_final_assignment.comment_service.exception.CommentNotFoundException;
import com.springboot_final_assignment.comment_service.exception.CommentsNotFoundException;
import com.springboot_final_assignment.comment_service.exception.PostNotFoundException;
import com.springboot_final_assignment.comment_service.repository.CommentRepository;
import com.springboot_final_assignment.comment_service.utils.Constants;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostClient postClient;



    @Override
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments=commentRepository.findAll();
        if(comments.size()==0)
            throw new CommentsNotFoundException(Constants.NO_COMMENTS);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Comment> getCommentById(int id) {
        Optional<Comment> comment=commentRepository.findById(id);
        if(comment.isPresent())
            return new ResponseEntity<>(comment.get(),HttpStatus.OK);
        else
            throw new RuntimeException(Constants.COMMENT_NOT_FOUND+id);
    }

    @Override
    public ResponseEntity<List<Comment>> getCommentsByPostId(int postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        if (comments.isEmpty()) {
            throw new CommentsNotFoundException(Constants.NO_COMMENTS_FOR_POST +postId);
        } else {
            return ResponseEntity.ok(comments);
        }
    }

    @Override
    public ResponseEntity<Comment> createComment(Comment comment,int postId) {
        try {
            postClient.checkIfPostExists(postId);
        } catch (FeignException.NotFound e) {
            throw new PostNotFoundException(Constants.POST_NOT_FOUND + comment.getPostId());
        }

        comment.setPostId(postId);
        commentRepository.save(comment);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Comment> updateComment(int id, Comment updatedComment) {
        Optional<Comment> existingCommentOptional = commentRepository.findById(id);

        if (existingCommentOptional.isPresent()) {
            Comment existingComment = existingCommentOptional.get();

            existingComment.setPostId(updatedComment.getPostId());
            existingComment.setUsername(updatedComment.getUsername());
            existingComment.setLikes(updatedComment.getLikes());
            existingComment.setReplies(updatedComment.getReplies());
            existingComment.setComment(updatedComment.getComment());
            commentRepository.save(existingComment);
            return new ResponseEntity<>(existingComment, HttpStatus.OK);
        } else {
            throw new CommentNotFoundException(Constants.COMMENT_NOT_FOUND + id);
        }
    }

    @Override
    public ResponseEntity<Void> deleteComment(int id) {
        Optional<Comment> optionalPost = commentRepository.findById(id);
        if (optionalPost.isPresent()) {
            commentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new CommentNotFoundException(Constants.COMMENT_NOT_FOUND + id);
        }
    }

    @Override
    public ResponseEntity<Void> deleteByPostId(int postId) {
        commentRepository.deleteByPostId(postId);
        return ResponseEntity.noContent().build();
    }

}
