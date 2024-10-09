package com.spring_boot_final_assginment.security_service.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerClass {
    @ExceptionHandler
    public ResponseEntity<ErrorResponseClass> handleException(InvalidUserException exc){
        ErrorResponseClass custom=new ErrorResponseClass();
        custom.setMessage(exc.getMessage());
        custom.setStatus(HttpStatus.UNAUTHORIZED.value());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(custom,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponseClass> handleException(InvalidRequestException exc){
        ErrorResponseClass custom=new ErrorResponseClass();
        custom.setMessage(exc.getMessage());
        custom.setStatus(HttpStatus.BAD_REQUEST.value());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(custom,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponseClass> handleInvalidDetailsExceptions(InvalidUserDetails exc) {
        ErrorResponseClass custom = new ErrorResponseClass();
        custom.setMessage(exc.getMessage());
        custom.setStatus(HttpStatus.BAD_REQUEST.value());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(custom, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponseClass> handleException(InvalidTokenException exc){
        ErrorResponseClass custom=new ErrorResponseClass();
        custom.setMessage(exc.getMessage());
        custom.setStatus(HttpStatus.UNAUTHORIZED.value());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(custom,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseClass> handleOtherExceptions(Exception exc) {
        ErrorResponseClass custom = new ErrorResponseClass();
        custom.setMessage("error occurred.");
        custom.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(custom, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
