package com.fullstackfidelity.campusconnect.DTO;

import com.fullstackfidelity.campusconnect.entities.post.Post;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PostDTO {
    private long postId;
    private int totalLikes;
    private Set<Long> likedByUsers;
    private List<CommentDTO> comments; // Assuming you have a CommentDTO
    private long userId; // User's ID
    private String username; // Optionally include the User's name
    private String content;
    private String imagePath;


    public PostDTO() {}

    public PostDTO(Post post) {
        this.postId = post.getPostId();
        this.totalLikes = post.getTotalLikes();
        this.likedByUsers = post.getLikedByUsers();
        this.comments = post.getComments().stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
        this.userId = post.getUser().getUser_id();
        this.username = post.getUser().getUsername(); // Assuming the User entity has a getUsername method
        this.content = post.getContent();
        this.imagePath = post.getImagePath();
    }
}

