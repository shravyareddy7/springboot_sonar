package com.spring_boot_final_assignment.api_gateway.exception;

public class UnAuthorizedUserException extends RuntimeException{
    public UnAuthorizedUserException(String message){
        super(message);
    }
}
