package com.spring_boot_final_assginment.security_service.exception;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException(String message){
        super(message);
    }
}
