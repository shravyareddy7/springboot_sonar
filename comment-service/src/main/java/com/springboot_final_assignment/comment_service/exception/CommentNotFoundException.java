package com.springboot_final_assignment.comment_service.exception;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(String message){
        super(message);
    }
}
