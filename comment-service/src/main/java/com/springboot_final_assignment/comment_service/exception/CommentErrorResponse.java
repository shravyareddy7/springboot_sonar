package com.springboot_final_assignment.comment_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
