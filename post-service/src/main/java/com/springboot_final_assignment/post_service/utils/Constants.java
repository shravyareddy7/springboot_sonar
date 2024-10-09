package com.springboot_final_assignment.post_service.utils;

public class Constants {
    public static final String COMMENT_SERVICE_URL = "http://localhost:8082";
    public static final String COMMENTS_BY_POST_ID = "/comments/post/{postId}";
    public static final String POSTS_URL = "/posts";
    public static final String POST_ID_URL = "/{id}";

    public static final String NO_COMMENTS_FOUND = "No comments found for post with ID: ";
    public static final String POST_NOT_FOUND = "Post not found with id ";
    public static final String POSTS_NOT_FOUND = "No posts available.";
    public static final String COMMENTS_FETCH_ERROR = "An error occurred while fetching comments for post ID ";
    public static final String NO_POST_WITH_ID = "No posts available.";
    public static final String FETCHING_POST = "Fetching post with ID: ";
}

