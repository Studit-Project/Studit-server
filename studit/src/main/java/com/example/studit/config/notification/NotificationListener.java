package com.example.studit.config.notification;

import com.example.studit.domain.notification.dto.NotificationRequestDto;
import com.example.studit.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificationListener {
    private final NotificationService notificationService;

    @TransactionalEventListener
    @Async
    public void handleNotification(NotificationRequestDto requestDto){
        notificationService.send(requestDto.getReceiver(), requestDto.getNotificationType(), requestDto.getContent(), requestDto.getUrl());
    }
}
