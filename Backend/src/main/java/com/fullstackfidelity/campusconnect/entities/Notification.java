package com.fullstackfidelity.campusconnect.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fullstackfidelity.campusconnect.entities.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "`notification`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String content;
    private String foundId;

    private String link;

    private Long targetPostId;
    private boolean isRead;
    @ElementCollection
    private List<Long> userIds;

    private LocalDateTime timestamp;
    private String senderUserName;
    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "user_notification",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @PrePersist
    public void setCreationTimestamp() {
        this.timestamp = LocalDateTime.now();
    }
}
