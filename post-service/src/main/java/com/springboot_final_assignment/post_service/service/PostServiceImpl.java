package com.springboot_final_assignment.post_service.service;

import com.springboot_final_assignment.post_service.client.CommentClient;
import com.springboot_final_assignment.post_service.dto.Comment;
import com.springboot_final_assignment.post_service.entity.Post;
import com.springboot_final_assignment.post_service.exception.PostNotFoundException;
import com.springboot_final_assignment.post_service.exception.PostsNotFoundException;
import com.springboot_final_assignment.post_service.repository.PostRepository;
import com.springboot_final_assignment.post_service.utils.Constants;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private PostRepository repository;

    private  CommentClient commentClient;
    public PostServiceImpl(CommentClient commentClient,PostRepository repository ) {
        this.commentClient = commentClient;
        this.repository=repository;
    }
    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = repository.findAll();
        if(repository.findAll().isEmpty())
            throw new PostNotFoundException(Constants.NO_POST_WITH_ID);
        for (Post post : posts) {
            try {
                List<Comment> comments = commentClient.getCommentsByPostId(post.getId());
                post.setComments(comments);
            } catch (Exception e) {
                post.setComments(new ArrayList<>());
            }
        }
        return posts;
    }

    @Override
    public Post getPostById(int id) {
        Optional<Post> optionalPost = repository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            try {
                List<Comment> comments = commentClient.getCommentsByPostId(id);
                post.setComments(comments);
            } catch (FeignException e) {
                if (e.status() == 404) {
                    post.setComments(new ArrayList<>());
                } else {
                    throw e;
                }
            }

            return optionalPost.get();
        } else {
            throw new PostNotFoundException(Constants.POST_NOT_FOUND + id);
        }
    }

    @Override
    public Post createPost(Post post) {
        return repository.save(post);
    }

    @Override
    public Post updatePost(int id, Post post) {
        Optional<Post> existingPost = repository.findById(id);
        System.out.println(existingPost);
        if (existingPost.isPresent()) {
            Post updatedPost = existingPost.get();
            updatedPost.setPictureUrl(post.getPictureUrl());
            updatedPost.setCaption(post.getCaption());
            updatedPost.setLikes(post.getLikes());
            updatedPost.setShares(post.getShares());
//            updatedPost.setId(id);
            repository.save(updatedPost);
            System.out.println(updatedPost);
            return updatedPost;
        } else {
            throw new PostsNotFoundException(Constants.POST_NOT_FOUND + id);
        }
    }

    @Override
    public void deletePost(int id) {
        Optional<Post> optionalPost = repository.findById(id);
        if (optionalPost.isPresent()) {
            repository.deleteById(id);
            commentClient.deleteCommentsByPostId(id);
        } else {
            throw new PostNotFoundException(Constants.POST_NOT_FOUND + id);
        }
    }

    @Override
    public Boolean existsById(int postId) {
        Optional<Post> optionalPost = repository.findById(postId);
        return optionalPost.isPresent();
    }

}
