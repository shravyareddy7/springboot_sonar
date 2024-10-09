package com.springboot_final_assignment.post_service.controller;

import com.springboot_final_assignment.post_service.client.CommentClient;
import com.springboot_final_assignment.post_service.entity.Post;
import com.springboot_final_assignment.post_service.service.PostService;
import com.springboot_final_assignment.post_service.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.POSTS_URL)
public class PostController {

    private PostService service;

    @Autowired
    private CommentClient commentClient;
    public PostController(CommentClient client,PostService service){
        commentClient=client;
        this.service=service;
    }

    @GetMapping
    public List<Post> getAllPosts(){
        return service.getAllPosts();
    }

    @GetMapping(Constants.POST_ID_URL)
    public Post getPost(@PathVariable int id){
        return service.getPostById(id);
    }

    @PostMapping
    public void createPost(@RequestBody Post post){
        service.createPost(post);
    }

    @PutMapping(Constants.POST_ID_URL)
    public Post updatePost(@PathVariable int id, @RequestBody Post post){
        service.updatePost(id,post);
        return post;
    }

    @DeleteMapping(Constants.POST_ID_URL)
    public void deletePost(@PathVariable int id){
        service.deletePost(id);
    }

    @GetMapping("/posts/")
    public Boolean checkIfPostExists(int id) {
        return service.existsById(id);
    }

}
