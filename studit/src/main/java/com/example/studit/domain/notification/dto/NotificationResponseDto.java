package com.example.studit.domain.notification.dto;

import com.example.studit.domain.User.dto.UserInfoDto;
import com.example.studit.domain.notification.Notification;
import com.example.studit.domain.notification.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponseDto {

    public static Object create(Notification notification) {
        return notification + "";
    }
}
