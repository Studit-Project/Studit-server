package com.example.studit.controller;

import com.example.studit.domain.User;
import com.example.studit.dto.JwtRequestDto;
import com.example.studit.dto.JwtResponseDto;
import com.example.studit.dto.UserJoinDto;
import com.example.studit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @PostMapping("/user/join")
    public Long join(@RequestBody @Validated UserJoinDto userJoinDto) {
        return userService.join(userJoinDto);
    }

    //로그인
    @PostMapping("/user/login")
    public JwtResponseDto login(@RequestBody JwtRequestDto request) throws Exception {
        return userService.login(request);
    }
}
