package com.springboot_final_assignment.post_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
