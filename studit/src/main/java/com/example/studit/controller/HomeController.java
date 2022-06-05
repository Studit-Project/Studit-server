package com.example.studit.controller;

import com.example.studit.config.swagger.BaseResponse;
import com.example.studit.dto.GetHomeRes;
import com.example.studit.service.HomeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;

    @ApiOperation("기본 홈")
    @GetMapping("/")
    public BaseResponse<GetHomeRes> getHome(){
        return new BaseResponse<GetHomeRes>(homeService.getInfo());
    }


}
