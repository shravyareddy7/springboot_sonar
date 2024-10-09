package com.spring_boot_final_assignment.api_gateway.exception;

public class MissingAuthHeaderException extends RuntimeException{
    public MissingAuthHeaderException(String message){
        super(message);
    }
}
