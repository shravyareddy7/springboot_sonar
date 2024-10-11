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
    public String createPost(@RequestBody Post post){
        service.createPost(post);
        return "Post saved";
    }

    @PutMapping(Constants.POST_ID_URL)
    public Post updatePost(@PathVariable int id, @RequestBody Post post){
        Post updatedPost = service.updatePost(id, post);
        return updatedPost;
    }

    @DeleteMapping(Constants.POST_ID_URL)
    public String deletePost(@PathVariable int id){
        service.deletePost(id);
        return "Deleted post with id "+id;
    }

    @GetMapping("/posts/")
    public Boolean checkIfPostExists(int id) {
        return service.existsById(id);
    }

}
