package com.fullstackfidelity.campusconnect.entities.post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fullstackfidelity.campusconnect.entities.comment.Comment;
import com.fullstackfidelity.campusconnect.entities.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Table(name = "`post`")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;
    private LocalDateTime creationTimestamp;

    @Column(name = "totalLikes", nullable = true)
    private int totalLikes;

    @ElementCollection
    @CollectionTable(name = "post_likes", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "user_id")
    private Set<Long> likedByUsers = new HashSet<>();
    @OneToMany(mappedBy = "freeZonePost", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Comment> comments;

    private long userIdValue;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(length = 280)
    private String content;

    private String imagePath;

    @PrePersist
    public void setCreationTimestamp() {
        this.creationTimestamp = LocalDateTime.now();
    }
    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", creationTimestamp=" + creationTimestamp +
                '}';
    }

}
