package com.fullstackfidelity.campusconnect.service;

import com.fullstackfidelity.campusconnect.entities.Notification;
import com.fullstackfidelity.campusconnect.entities.user.User;
import com.fullstackfidelity.campusconnect.exception.UserNotFoundException;
import com.fullstackfidelity.campusconnect.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private NotificationRepository notificationRepository;

    private UserService userService;

    public NotificationService(UserService userService, NotificationRepository notificationRepository)
    {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }
    public List<Notification> findNotificationsByUserId(Long userId) {
        return notificationRepository.findNotificationsForUser(userId);
    }
    public Notification createNotificationForCard(Notification notification)
    {
        Set<User> users = new HashSet<>();
        User user = userService.getUserByBilkentId(notification.getFoundId()).orElseThrow(() -> new UserNotFoundException("User with id: " + notification.getFoundId() + " not found!"));
        users.add(user);
        notification.setUsers(users);
        notification.setUserIds(users.stream()
                .map(User::getUser_id)
                .collect(Collectors.toList()));

        return notificationRepository.save(notification);
    }
    public Notification createNotification(Notification notification, List<Long> userIds) {
        Set<User> users = new HashSet<>();

        for (Long userId : userIds) {
            User user = userService.getUserById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " not found!"));
            users.add(user);
        }

        notification.setUsers(users);
        notification.setUserIds(users.stream()
                .map(User::getUser_id)
                .collect(Collectors.toList()));

        return notificationRepository.save(notification);
    }
    public Notification updateNotification(Long id, Notification notificationDetails) {
        Notification notification = notificationRepository.findById(id).orElse(null);
        if (notification != null) {
            notification.setContent(notificationDetails.getContent());
            notification.setLink(notificationDetails.getLink());
            return notificationRepository.save(notification);
        }
        return null;
    }

    public Notification readNotification(long id,int booleanVal){
        Notification notification = notificationRepository.findById(id).orElse(null);
        if(booleanVal == 0){
            notification.setRead(false);
        }else
        {
            notification.setRead(true);
        }
        return notificationRepository.save(notification);
    }
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
