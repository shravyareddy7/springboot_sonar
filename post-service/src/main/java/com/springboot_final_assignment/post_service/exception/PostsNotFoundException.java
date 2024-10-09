package com.springboot_final_assignment.post_service.exception;


public class PostsNotFoundException extends RuntimeException{
    public PostsNotFoundException(String message){
        super(message);
    }
}
