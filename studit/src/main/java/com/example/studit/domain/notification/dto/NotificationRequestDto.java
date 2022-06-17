package com.example.studit.domain.notification.dto;

import com.example.studit.domain.User.User;
import com.example.studit.domain.notification.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequestDto {
    private User receiver;
    private NotificationType notificationType;
    private String content;
    private String url;
}
