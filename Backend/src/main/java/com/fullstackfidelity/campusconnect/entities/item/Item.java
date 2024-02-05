package com.fullstackfidelity.campusconnect.entities.item;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fullstackfidelity.campusconnect.entities.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Inheritance
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "`item`")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemId;

    @Column(nullable = false)
    private String itemName;
    @Column(nullable = false)
    private String imagePath;
    @Column(nullable = false)
    private String description;
    @Column()
    private String status;
    @Column
    private Long userIdValue;
    private LocalDate postDate;
    private LocalDate retrieveDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    @JsonBackReference
    private User user;
    @PrePersist
    public void setCreationTimestamp() {
        this.postDate = LocalDate.now();
        this.retrieveDate = LocalDate.now();//bunu ilk göstermeyelim not null erroru için eşitledim table droplanması gerekecekti öbür türlü
    }
}