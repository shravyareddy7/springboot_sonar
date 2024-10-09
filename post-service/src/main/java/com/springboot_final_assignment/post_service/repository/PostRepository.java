package com.springboot_final_assignment.post_service.repository;

import com.springboot_final_assignment.post_service.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
