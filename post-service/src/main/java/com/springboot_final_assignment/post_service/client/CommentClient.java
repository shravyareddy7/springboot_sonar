package com.springboot_final_assignment.post_service.client;

import com.springboot_final_assignment.post_service.dto.Comment;
import com.springboot_final_assignment.post_service.utils.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "comment-service", url = Constants.COMMENT_SERVICE_URL)
public interface CommentClient {

    @GetMapping(Constants.COMMENTS_BY_POST_ID)
    List<Comment> getCommentsByPostId(@PathVariable int postId);

    @DeleteMapping(Constants.COMMENTS_BY_POST_ID)
    void deleteCommentsByPostId(@PathVariable("postId") int postId);
}
