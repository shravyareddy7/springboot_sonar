package com.springboot_final_assignment.comment_service.utils;

public class Constants {
    public static final String SERVICE_URL = "http://localhost:8081";
    public static final String CHECK_POST_EXISTS = "posts/{id}";
    public static final String COMMENTS_BASE_URL = "/comments";
    public static final String GET_ALL_COMMENTS = "";
    public static final String GET_COMMENT_BY_ID = "/{id}";
    public static final String GET_COMMENTS_BY_POST_ID = "/post/{postId}";
    public static final String CREATE_COMMENT = "";
    public static final String UPDATE_COMMENT = "/{id}";
    public static final String DELETE_COMMENT = "/{id}";

    public static final String NO_COMMENTS = "No comments yet.";
    public static final String COMMENT_NOT_FOUND = "Comment not found with id ";
    public static final String POST_NOT_FOUND = "Post not found with id ";
    public static final String NO_COMMENTS_FOR_POST = "There are no comments for the post with the post id ";

}


