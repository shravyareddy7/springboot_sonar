package com.springboot_final_assignment.comment_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

   @ExceptionHandler
    public ResponseEntity<CommentErrorResponse> handleException(CommentsNotFoundException exception){
       CommentErrorResponse response=new CommentErrorResponse();
       response.setMessage(exception.getMessage());
       response.setTimeStamp(System.currentTimeMillis());
       response.setStatus(HttpStatus.NOT_FOUND.value());
       return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
   }

    @ExceptionHandler
    public ResponseEntity<CommentErrorResponse> handleException(CommentNotFoundException exception){
        CommentErrorResponse response=new CommentErrorResponse();
        response.setMessage(exception.getMessage());
        response.setTimeStamp(System.currentTimeMillis());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<CommentErrorResponse> handlePostNotFoundException(PostNotFoundException exception) {
        CommentErrorResponse response = new CommentErrorResponse();
        response.setMessage(exception.getMessage());
        response.setTimeStamp(System.currentTimeMillis());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommentErrorResponse> handleException(Exception exc){
        CommentErrorResponse custom=new CommentErrorResponse();
        custom.setStatus(HttpStatus.BAD_REQUEST.value());
        custom.setMessage("Invalid Url");
        custom.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(custom,HttpStatus.BAD_REQUEST);
    }
}
