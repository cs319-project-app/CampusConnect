package com.fullstackfidelity.campusconnect.entities.user;

import com.fasterxml.jackson.annotation.*;
import com.fullstackfidelity.campusconnect.entities.Notification;
import com.fullstackfidelity.campusconnect.entities.comment.Comment;
import com.fullstackfidelity.campusconnect.entities.item.Item;
import com.fullstackfidelity.campusconnect.entities.post.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "`user`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private long user_id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String bilkentMail;

    @Column(unique = true, nullable = false)
    private String bilkentID;

    @ManyToMany(mappedBy = "users")
    private Set<Notification> notifications = new HashSet<>();

    private String code;
    private double ratingForBorrow=0;

    private double ratingForSecondHand=0;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Item> history = new ArrayList<>();

    private String userPhotoPath = "assets/person/person.png";

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_blocks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "blocked_user_id"))
    @JsonIgnore
    private List<User> blockedUsers = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Item> postedItems = new ArrayList<>();

}
