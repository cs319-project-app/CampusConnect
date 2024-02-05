package com.fullstackfidelity.campusconnect.entities.item;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fullstackfidelity.campusconnect.entities.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "`idCard`")
public class IdCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemId;

    private long userIdValue;
    @Column(unique = true, nullable = false)
    private String losedIdNo;
    @Column(nullable = false)
    private String idLoserName;
    @Column(nullable = false)
    private String idLoserDepartment;

    private String founderId;
    private String location = "G binası";
    private String foundInLocation;
    private String foundTime;

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
