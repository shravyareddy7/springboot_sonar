package com.spring_boot_final_assginment.security_service.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(InvalidUserException exc){
        ErrorResponse custom=new ErrorResponse();
        custom.setMessage(exc.getMessage());
        custom.setStatus(HttpStatus.UNAUTHORIZED.value());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(custom,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(InvalidRequestException exc){
        ErrorResponse custom=new ErrorResponse();
        custom.setMessage(exc.getMessage());
        custom.setStatus(HttpStatus.BAD_REQUEST.value());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(custom,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidDetailsExceptions(InvalidUserDetails exc) {
        ErrorResponse custom = new ErrorResponse();
        custom.setMessage(exc.getMessage());
        custom.setStatus(HttpStatus.BAD_REQUEST.value());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(custom, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(InvalidTokenException exc){
        ErrorResponse custom=new ErrorResponse();
        custom.setMessage(exc.getMessage());
        custom.setStatus(HttpStatus.UNAUTHORIZED.value());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(custom,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception exc) {
        ErrorResponse custom = new ErrorResponse();
        custom.setMessage("error occurred.");
        custom.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(custom, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
