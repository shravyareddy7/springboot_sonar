package com.spring_boot_final_assignment.api_gateway.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;

@ControllerAdvice
public class ExceptionHandlerClass {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomExceptionClass> handleTypeMismatch(MethodArgumentTypeMismatchException exc) {
        CustomExceptionClass custom = new CustomExceptionClass();
        custom.setMessage("Invalid value for parameter: " + exc.getName() + ". Expected type: " + Objects.requireNonNull(exc.getRequiredType()).getSimpleName());
        custom.setStatus(HttpStatus.BAD_REQUEST.value());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(custom, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidUserAccessException.class)
    public ResponseEntity<CustomExceptionClass> handleInvalidUserException(InvalidUserAccessException exc){
        CustomExceptionClass custom=new CustomExceptionClass();
        custom.setMessage(exc.getMessage());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        custom.setStatus(HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(custom, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<CustomExceptionClass> handleException(InvalidRequestException exc){
        CustomExceptionClass custom=new CustomExceptionClass();
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        custom.setMessage("Invalid Url provide");
        custom.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(custom,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MissingAuthHeaderException.class)
    public ResponseEntity<CustomExceptionClass> handleMissingAuthHeaderException(MissingAuthHeaderException exc){
        CustomExceptionClass custom=new CustomExceptionClass();
        custom.setMessage(exc.getMessage());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        custom.setStatus(HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(custom,HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(UnAuthorizedUserException.class)
    public ResponseEntity<CustomExceptionClass> handleUnAuthorizedException(UnAuthorizedUserException exc){
        CustomExceptionClass custom=new CustomExceptionClass();
        custom.setMessage(exc.getMessage());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        custom.setStatus(HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(custom,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomExceptionClass> handleOtherExceptions(Exception exc) {
        CustomExceptionClass custom = new CustomExceptionClass();
        custom.setMessage("An error occurred.");
        custom.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        custom.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(custom, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
