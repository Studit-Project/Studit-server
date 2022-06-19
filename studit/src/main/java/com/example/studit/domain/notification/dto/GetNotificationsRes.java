package com.example.studit.domain.notification.dto;

import com.example.studit.domain.User.User;
import com.example.studit.domain.User.dto.UserInfoDto;
import com.example.studit.domain.notification.Notification;
import com.example.studit.domain.notification.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetNotificationsRes {
    private Long id;
    private String content;
    private Boolean isRead;
    private NotificationType notificationType;
    private UserInfoDto receiver;

    public GetNotificationsRes(Notification notification) {
        this.id = notification.getId();
        this.content = notification.getContent();
        this.isRead = notification.getIsRead();
        this.notificationType = notification.getNotificationType();
        this.receiver = notification.getReceiver().toUserInfoDto();
    }
}
