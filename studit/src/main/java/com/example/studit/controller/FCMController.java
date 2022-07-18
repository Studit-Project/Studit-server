package com.example.studit.controller;

import com.example.studit.config.exception.BaseResponse;
import com.example.studit.domain.User.User;
import com.example.studit.domain.notification.NotificationType;
import com.example.studit.service.FCMService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/push")
public class FCMController {

    private final FCMService fcmService;

    @Autowired
    public FCMController(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    @ApiOperation("fcm 토큰 전송")
    @PatchMapping("/{userId}")
    public BaseResponse<Long> addFcmToken(@PathVariable(name = "userId") Long userId ,@RequestParam("fcmToken") String fcmToken){
        User user = fcmService.addFcmToken(userId, fcmToken);
        return new BaseResponse<Long>(user.getId());
    }


    @GetMapping("/test")
    public String tokenTest() throws IOException {
       // String testToken = "fpO9B9HkQs-5nUrb0t-2de:APA91bHSK_JgiUJDL4re0dSfEyVAgNwp7C6Cugc05vUoXxg3WiHv6fubih7_czuJggVq6Yl4DUwwQIlzvz2qkVHh7IGPwQnQprd6ZN-6MXmo1jfogRX1t4WQK5ABmT-XaKOkVw_Wa39q";
        fcmService.sendMessageTo(Long.valueOf(1), NotificationType.COMMENT);
        return "itistest";
    }


}
