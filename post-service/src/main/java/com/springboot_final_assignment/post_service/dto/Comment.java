package com.springboot_final_assignment.post_service.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int id;
    private int postId;
    private String comment;
    private String username;
    private int likes;

    private ArrayList<String> replies=new ArrayList<>();
}
