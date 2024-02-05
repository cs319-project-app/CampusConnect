package com.fullstackfidelity.campusconnect.controller;

import com.fullstackfidelity.campusconnect.DTO.NotificationData;
import com.fullstackfidelity.campusconnect.entities.Notification;
import com.fullstackfidelity.campusconnect.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/notifications")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @PostMapping("/{notificationId}/read/{booleanZeroBit}")
    public ResponseEntity<Notification> readNotification(@PathVariable long notificationId,@PathVariable(required = false)  Integer booleanZeroBit)
    {
        int bitValue = (booleanZeroBit != null) ? booleanZeroBit : 1;
        Notification notification = notificationService.readNotification(notificationId,bitValue);
        return  ResponseEntity.ok(notification);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.findNotificationsByUserId(userId);
        if (notifications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notifications);
    }
    @GetMapping("/notificationById/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Notification notification = notificationService.getNotificationById(id);
        if (notification != null) {
            return ResponseEntity.ok(notification);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody NotificationData notificationData) {
        Notification createdNotification = notificationService.createNotification(notificationData.getNotification(), notificationData.getUserIds());
        return ResponseEntity.ok(createdNotification);
    }

    @PostMapping("/cardReport")
    public ResponseEntity<Notification> createNotificationPost(@RequestBody Notification notification)
    {
        Notification created = notificationService.createNotificationForCard(notification);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @RequestBody Notification notificationDetails) {
        Notification updatedNotification = notificationService.updateNotification(id, notificationDetails);
        if (updatedNotification != null) {
            return ResponseEntity.ok(updatedNotification);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok().build();
    }
}
