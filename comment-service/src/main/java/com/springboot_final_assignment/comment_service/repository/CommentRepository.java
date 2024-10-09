package com.springboot_final_assignment.comment_service.repository;

import com.springboot_final_assignment.comment_service.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer>{

    List<Comment> findByPostId(int postId);

    void deleteByPostId(int postId);
}
