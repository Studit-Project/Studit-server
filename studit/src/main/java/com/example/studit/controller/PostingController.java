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

@RequestMapping("/posting")
@RestController
@RequiredArgsConstructor
public class PostingController {

    @Autowired
    private final PostingService postingService;

    @Autowired
    private final UserService userService;

    @ApiOperation("새로운 글 작성")
    @PostMapping("/new")
    public BaseResponse<Long> createPosting(@RequestBody PostCreateReq postCreateReq){
        return new BaseResponse<Long>(postingService.save(postCreateReq));
    }

    @ApiOperation("스터디 전체 글 보기")
    @GetMapping("/")
    public BaseResponse<List<PostingListDto>> studyPostings(){
        return new BaseResponse<List<PostingListDto>>(postingService.findAllStudyPosting("STUDY"));
    }

    @ApiOperation("글 상세 보기")
    @GetMapping("/{postingId}")
    public BaseResponse<PostingDto> read(@PathVariable("postingId") Long postingId){
        return new BaseResponse<PostingDto>(postingService.readOne(postingId));
    }

    /**검색
      직업(고등학생, 대학생, 직장인, 기타)
      성별(여성, 남성, 모두)
      지역(강원도, 서울, 경북, 대구, 경기도, 부산, 전남, 광주, 제주도, 충남, 대전, 경남, 충북, 전북, 인천)
      상태(온라인, 오프라인, 병합)
     **/

    @ApiOperation("직접 키워드 검색")
    @GetMapping("/search/{keyword}")
    public BaseResponse<List<PostingListDto>> search(@PathVariable("keyword") String keyword){
        return new BaseResponse<>(postingService.findPostingsByKeyword(keyword));
    }





}
