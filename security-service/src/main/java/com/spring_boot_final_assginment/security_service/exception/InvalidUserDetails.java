package com.spring_boot_final_assginment.security_service.exception;

public class InvalidUserDetails extends RuntimeException{
    public InvalidUserDetails(String message){
        super(message);
    }
}
