package com.example.studit.controller;

import com.example.studit.domain.User;
import com.example.studit.dto.JwtRequestDto;
import com.example.studit.dto.JwtResponseDto;
import com.example.studit.dto.UserJoinDto;
import com.example.studit.service.NumberAuthenticationService;
import com.example.studit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final NumberAuthenticationService numberAuthenticationService;

    //회원가입
    @PostMapping("/user/join")
    public Long join(@RequestBody @Validated UserJoinDto userJoinDto) {
        return userService.join(userJoinDto);
    }

    //번호 인증 문자 전송
    @GetMapping("/user/check")
    public @ResponseBody String sendSMS(String phone){
        Random random = new Random();
        String numStr = "";

        for(int i = 0 ; i <4; i++){
            String ran = Integer.toString(random.nextInt(10));
            numStr += ran;
        }

        System.out.println("수신자 번호: " + phone);
        System.out.println("인증번호: " + numStr);
        numberAuthenticationService.sendMessage(phone, numStr);

        return numStr;
    }

    //로그인
    @PostMapping("/user/login")
    public JwtResponseDto login(@RequestBody JwtRequestDto request) throws Exception {
        return userService.login(request);
    }
}
