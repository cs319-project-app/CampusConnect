package com.fullstackfidelity.campusconnect.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fullstackfidelity.campusconnect.entities.comment.Comment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private long commentId;
    private LocalDateTime creationTimestamp;
    private String commentContent;
    private long likes;
    private Long parentCommentId; // ID of the parent comment if it exists
    private List<CommentDTO> replies; // Nested DTOs for replies
    private long postId; // ID of the associated post
    private long userId; // ID of the user who made the comment
    private String username; // Optionally include the user's name

    public CommentDTO(Comment comment) {
        this.commentId = comment.getCommentId();
        this.creationTimestamp = comment.getCreationTimestamp();
        this.commentContent = comment.getCommentContent();
        this.likes = comment.getLikes();
        this.parentCommentId = (comment.getParentComment() != null) ? comment.getParentComment().getCommentId() : null;
        this.replies = comment.getReplies() != null ? comment.getReplies().stream().map(CommentDTO::new).collect(Collectors.toList()) : null;
        this.postId = (comment.getFreeZonePost() != null) ? comment.getFreeZonePost().getPostId() : null;
        this.userId = (comment.getUser() != null) ? comment.getUser().getUser_id() : null;
        this.username = (comment.getUser() != null) ? comment.getUser().getUsername() : null; // Assuming the User entity has a getUsername method
    }
}
