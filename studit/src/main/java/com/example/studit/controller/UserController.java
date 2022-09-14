package com.example.studit.controller;

import com.example.studit.config.exception.BaseException;
import com.example.studit.config.exception.BaseResponse;
import com.example.studit.domain.User.User;
import com.example.studit.domain.User.dto.PatchDetailReq;
import com.example.studit.domain.User.dto.StatusMessage;
import com.example.studit.domain.invitation.dto.GetAllRes;
import com.example.studit.dto.AuthenticationRes;
import com.example.studit.dto.JwtRequestDto;
import com.example.studit.dto.JwtResponseDto;
import com.example.studit.domain.User.dto.UserJoinDto;
import com.example.studit.dto.Phone;
import com.example.studit.service.NumberAuthenticationService;
import com.example.studit.service.StudyService;
import com.example.studit.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    private final StudyService studyService;

    @ApiOperation("회원가입")
    @PostMapping("/join")
    public BaseResponse<Long> join(@RequestBody @Validated UserJoinDto userJoinDto) throws BaseException {
        return new BaseResponse<Long>(userService.join(userJoinDto));
    }

    @ApiOperation("회원 세부 정보 설정")
    @PatchMapping("/join/detail/{userId}")
    public BaseResponse<String> configDetail(@PathVariable(name = "userId") Long userId, @RequestBody @Validated PatchDetailReq patchDetailReq) throws BaseException {
        userService.addDetailInfo(userId, patchDetailReq);
        return new BaseResponse<String>("");
    }

    @ApiOperation("번호 인증 문자 전송")
    @PostMapping("/check")
    protected @ResponseBody BaseResponse<String> sendSMS(@RequestBody Phone phone){
        Random random = new Random();
        String numStr = "";

        for(int i = 0 ; i <4; i++){
            String ran = Integer.toString(random.nextInt(10));
            numStr += ran;
        }

        System.out.println("수신자 번호: " + phone);
        System.out.println("인증번호: " + numStr);
        numberAuthenticationService.sendMessage(phone.getPhone(), numStr);

        AuthenticationRes authenticationRes = new AuthenticationRes(numStr);

        return new BaseResponse<>(numStr);
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

    @ApiOperation("초대 수락 여부")
    @PatchMapping("/invitation/{invitationId}")
    public BaseResponse<String> accept(@PathVariable(name = "invitationId") Long invitationId, @RequestParam boolean acceptance) throws IOException {
        studyService.accept(invitationId, acceptance);
        return new BaseResponse<String>("");
    }

    @ApiOperation("상태 메세지만 수정")
    @PatchMapping("/info/message")
    public BaseResponse<String> editStatusMessage(@RequestBody StatusMessage statusMessage) {
        return new BaseResponse<String>(userService.editStatusMessage(statusMessage));
    }

    @ApiOperation("패스워드 수정")
    @PatchMapping("/password/modify")
    public BaseResponse<Long> modifyPassword(@RequestParam("password") String password){
        User user = userService.modifyPassword(password);
        return new BaseResponse<Long>(user.getId());
    }

    @ApiOperation("임시 비밀번호 발급")
    @GetMapping("/password/find")
    public BaseResponse<String> findPassword(@RequestParam("email") String email) throws BaseException {
        //비밀번호 찾기를 누르면 1. 이메일로 유저 체크 2. 이메일로 임시 비밀번호 발급
        return new BaseResponse<String>(userService.findPassword(email) + "로 이메일이 발송되었습니다.");
    }

}
