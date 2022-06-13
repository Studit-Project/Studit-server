package com.example.studit.controller;

import com.example.studit.config.swagger.BaseResponse;
import com.example.studit.domain.User.dto.PatchDetailReq;
import com.example.studit.domain.invitation.dto.GetAllRes;
import com.example.studit.dto.JwtRequestDto;
import com.example.studit.dto.JwtResponseDto;
import com.example.studit.domain.User.dto.UserJoinDto;
import com.example.studit.service.NumberAuthenticationService;
import com.example.studit.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final NumberAuthenticationService numberAuthenticationService;

    @ApiOperation("회원가입")
    @PostMapping("/join")
    public BaseResponse<Long> join(@RequestBody @Validated UserJoinDto userJoinDto) {
        return new BaseResponse<Long>(userService.join(userJoinDto));
    }

    @ApiOperation("회원 세부 정보 설정")
    @PatchMapping("join/detail")
    public BaseResponse<String> configDetail(@RequestBody PatchDetailReq patchDetailReq){
        userService.addDetailInfo(patchDetailReq);
        return new BaseResponse<String>("");
    }

    @ApiOperation("번호 인증 문자 전송")
    @GetMapping("/check")
    protected @ResponseBody String sendSMS(String phone){
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

    @ApiOperation("로그인")
    @PostMapping("/login")
    public BaseResponse<JwtResponseDto> login(@RequestBody JwtRequestDto request) throws Exception {
        return new BaseResponse<JwtResponseDto>(userService.login(request));
    }

    @ApiOperation("스터디 초대란")
    @GetMapping("/invitation")
    public BaseResponse<List<GetAllRes>> manageInvitation(){
        return new BaseResponse<>(userService.getInvitations());
    }
}
