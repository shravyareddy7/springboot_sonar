package com.springboot_final_assignment.comment_service.client;

import com.springboot_final_assignment.comment_service.dto.Post;
import com.springboot_final_assignment.comment_service.utils.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "post-service", url = Constants.SERVICE_URL)
public interface PostClient {
    @GetMapping(Constants.CHECK_POST_EXISTS)
    boolean checkIfPostExists(@PathVariable("id") int postId);

    @GetMapping("/posts")
    public Post getPost(@PathVariable int id);
}
