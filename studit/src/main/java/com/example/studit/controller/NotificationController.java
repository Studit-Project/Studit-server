package com.example.studit.controller;

import com.example.studit.config.exception.BaseResponse;
import com.example.studit.domain.notification.dto.GetNotificationsRes;
import com.example.studit.service.NotificationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    /** @title 로그인 한 유저 sse 연결 **/
    @ApiOperation(value = "알림 구독", notes = "알림을 구독한다.")
    @GetMapping(value = "/subscribe/{id}", produces = "text/event-stream")
    public BaseResponse<SseEmitter> subscribe(@PathVariable Long id, @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId){
        return new BaseResponse<SseEmitter>(notificationService.subscribe(id, lastEventId));
    }

    /** 개인 알림 확인 **/
    @ApiOperation("알림 확인")
    @GetMapping("/user/notification")
    public BaseResponse<List<GetNotificationsRes>> getAll() {
        return new BaseResponse<List<GetNotificationsRes>>(notificationService.getAll());
    }

}
