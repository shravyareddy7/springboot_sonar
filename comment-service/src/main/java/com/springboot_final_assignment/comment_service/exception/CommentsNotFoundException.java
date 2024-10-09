package com.springboot_final_assignment.comment_service.exception;


public class CommentsNotFoundException extends RuntimeException{
    public CommentsNotFoundException(String message){
        super(message);
    }
}
