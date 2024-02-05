package com.fullstackfidelity.campusconnect.repositories;

import com.fullstackfidelity.campusconnect.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n JOIN n.users u WHERE u.user_id = :userId")
    List<Notification> findNotificationsForUser(Long userId);
}
