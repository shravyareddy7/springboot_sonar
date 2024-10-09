package com.spring_boot_final_assignment.api_gateway.exception;

public class InvalidUserAccessException extends RuntimeException{
    public InvalidUserAccessException(String message){
        super(message);
    }
}
