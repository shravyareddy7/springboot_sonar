package com.spring_boot_final_assignment.api_gateway.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomExceptionClass {

    private int status;

    private String message;

    private String timestamp;
}
