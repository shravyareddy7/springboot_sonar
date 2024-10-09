package com.springboot_final_assignment.post_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class RestExceptionHandler {

   @ExceptionHandler
    public ResponseEntity<PostErrorResponse> handleException(PostsNotFoundException exception){
       PostErrorResponse response=new PostErrorResponse();
       response.setMessage(exception.getMessage());
       response.setTimeStamp(System.currentTimeMillis());
       response.setStatus(HttpStatus.NOT_FOUND.value());
       return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
   }

    @ExceptionHandler
    public ResponseEntity<PostErrorResponse> handleException(PostNotFoundException exception){
        PostErrorResponse response=new PostErrorResponse();
        response.setMessage(exception.getMessage());
        response.setTimeStamp(System.currentTimeMillis());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<PostErrorResponse> handleException(Exception exc){
        PostErrorResponse custom=new PostErrorResponse();
        custom.setStatus(HttpStatus.BAD_REQUEST.value());
        custom.setMessage("Invalid Url");
        custom.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(custom,HttpStatus.BAD_REQUEST);
    }
}
