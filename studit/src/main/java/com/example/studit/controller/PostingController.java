package com.example.studit.controller;

import com.example.studit.config.swagger.BaseResponse;
import com.example.studit.domain.posting.dto.PostCreateReq;
import com.example.studit.dto.PostingDto;
import com.example.studit.dto.PostingListDto;
import com.example.studit.service.PostingService;
import com.example.studit.service.UserService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("새로운 글 작성")
    @PostMapping("/posting/new")
    public BaseResponse<Long> createPosting(@RequestBody PostCreateReq postCreateReq){
        return new BaseResponse<Long>(postingService.save(postCreateReq));
    }

    /*스터디 전체 글 보기*/
    @ApiOperation("스터디 전체 글 보기")
    @GetMapping("/posting")
    public BaseResponse<List<PostingListDto>> studyPostings(){
        return new BaseResponse<List<PostingListDto>>(postingService.findAllStudyPosting("STUDY"));
    }

    /*글 상세 보기*/
    @ApiOperation("글 상세 보기")
    @GetMapping("/posting/{postingId}")
    public BaseResponse<PostingDto> read(@PathVariable("postingId") Long postingId){
        return new BaseResponse<PostingDto>(postingService.readOne(postingId));
    }

}
