package com.fullstackfidelity.campusconnect.DTO;

import com.fullstackfidelity.campusconnect.entities.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NotificationData {
    private Notification notification;
    private List<Long> userIds;
}
