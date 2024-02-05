package com.fullstackfidelity.campusconnect.entities.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fullstackfidelity.campusconnect.entities.post.Post;
import com.fullstackfidelity.campusconnect.entities.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Inheritance
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    private LocalDateTime creationTimestamp;


    @Column(nullable = false, length = 500)
    private String commentContent;
    private long likes = 0;
    private long userIdValue;
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    @JsonIgnore
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> replies;

    @ManyToOne
    @JoinColumn(name = "freeZonePost_id")
    private Post freeZonePost;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @PrePersist
    public void setCreationTimestamp() {
        this.creationTimestamp = LocalDateTime.now();
    }
    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", creationTimestamp=" + creationTimestamp +
                '}';
    }

}
