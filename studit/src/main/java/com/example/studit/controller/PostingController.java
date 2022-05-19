package com.example.studit.controller;

import com.example.studit.domain.Posting;
import com.example.studit.domain.User;
import com.example.studit.dto.PostingDto;
import com.example.studit.dto.PostingListDto;
import com.example.studit.dto.UserInfoDto;
import com.example.studit.service.PostingService;
import com.example.studit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /*스터디 전체 글 보기*/
    @GetMapping("/posting")
    public List<PostingListDto> studyPostings(){
        return postingService.findAllStudyPosting("STUDY");
    }

    /*글 상세 보기*/
    @GetMapping("/posting/{postingId}")
    public PostingDto read(@PathVariable("postingId") Long postingId){
        return postingService.readOne(postingId);
    }

}
