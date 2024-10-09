package com.springboot_final_assignment.post_service.entity;


import com.springboot_final_assignment.post_service.dto.Comment;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String pictureUrl;
    private String caption;
    private int likes;
    private int shares;

    @Transient
    private List<Comment> comments=new ArrayList<>();

    public Post(String pictureUrl, String caption, int likes, int shares) {
        this.pictureUrl = pictureUrl;
        this.caption = caption;
        this.likes = likes;
        this.shares = shares;
    }

}
