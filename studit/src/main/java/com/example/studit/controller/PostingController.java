package com.example.studit.controller;

import com.example.studit.domain.User;
import com.example.studit.dto.PostingDto;
import com.example.studit.dto.UserInfoDto;
import com.example.studit.service.PostingService;
import com.example.studit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostingController {

    @Autowired
    private final PostingService postingService;

    @Autowired
    private final UserService userService;

    @PostMapping("/posting/create")
    public Long createPosting(@RequestBody PostingDto postingDto){
        return postingService.save(postingDto);
    }

}
