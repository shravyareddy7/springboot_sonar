package com.spring_boot_final_assginment.security_service.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseClass {
    private int status;
    private String message;

    private String timestamp;
}
