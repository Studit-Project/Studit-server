package com.example.studit.controller;

import com.example.studit.config.auth.dto.OAuthResponse;
import com.example.studit.config.exception.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Component
public class AuthController {

    @ApiOperation(value = "로그인 완료후 리다이렉트")
    @GetMapping("/auth")
    @ResponseBody
    public BaseResponse<OAuthResponse> jwtResponse(@RequestParam String jwt, @RequestParam Long id) {
        System.out.println("jwt: " + jwt + "!!!!!!!!!!!!!!");
        return new BaseResponse<>(new OAuthResponse(id,jwt));
    }

}
