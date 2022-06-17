package com.example.studit.controller;

import com.example.studit.config.swagger.BaseResponse;
import com.example.studit.domain.enumType.Gender;
import com.example.studit.domain.enumType.Target;
import com.example.studit.domain.posting.Province;
import com.example.studit.domain.posting.dto.PatchPostingReq;
import com.example.studit.domain.posting.dto.PostCreateReq;
import com.example.studit.domain.study.Activity;
import com.example.studit.domain.posting.dto.PostingDto;
import com.example.studit.domain.posting.dto.PostingListDto;
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
    @GetMapping("")
    public BaseResponse<List<PostingListDto>> studyPostings(){
        return new BaseResponse<List<PostingListDto>>(postingService.findAllStudyPosting("STUDY"));
    }

    @ApiOperation("글 상세 보기")
    @GetMapping("/{postingId}")
    public BaseResponse<PostingDto> read(@PathVariable(name = "postingId") Long postingId){
        return new BaseResponse<PostingDto>(postingService.readOne(postingId));
    }

    @ApiOperation("좋아요 누르기")
    @PostMapping("/{postingId}")
    public BaseResponse<String> likesPosting(@PathVariable(name = "postingId") Long postingId){
        postingService.likePosting(postingId);
        return new BaseResponse<String>("");
    }

    /**검색
      직업(고등학생, 대학생, 직장인, 기타)
      성별(여성, 남성, 모두)
      지역(강원도, 서울, 경북, 대구, 경기도, 부산, 전남, 광주, 제주도, 충남, 대전, 경남, 충북, 전북, 인천)
      활동(온라인, 오프라인, 병합)
     **/

    @ApiOperation("직접 키워드 검색")
    @GetMapping("/search/{keyword}")
    public BaseResponse<List<PostingListDto>> search(@PathVariable(name = "keyword") String keyword){
        return new BaseResponse<>(postingService.findPostingsByKeyword(keyword));
    }

    @ApiOperation("필터 검색")
    @GetMapping("/search/filter")
    public BaseResponse<List<PostingListDto>> searchByFilter(@RequestParam(required = false, defaultValue = "HIGH_SCHOOL, UNIVERSITY, JOB_SEEKER, OFFICE_WORKER") List<Target> targets, @RequestParam(required = false, defaultValue = "FEMALE, MALE, MIX") List<Gender> genders, @RequestParam(required = false, defaultValue = "SEOUL, BUSAN, DAEGU, INCHEON, GWANGJU, DAEJEON, ULSAN, SEJONG, GYENGGI, GANGWON, CHUNGBUK, CHUNGNAM, JEONBUK, JEONNAM, GYEONGBUK, GYENGNAM, JEJU") List<Province> provinces, @RequestParam(required = false, defaultValue = "ONLINE, OFFLINE, INTEGRATION") List<Activity> activities){
        return new BaseResponse<List<PostingListDto>>(postingService.findByFilter(targets, genders, provinces, activities));
    }

    @ApiOperation("포스팅 수정")
    @PatchMapping("/{postingId}")
    public BaseResponse<String> updatePosting(@PathVariable(name = "postingId") Long postingId, @RequestBody PatchPostingReq patchPostingReq) {
        postingService.update(postingId, patchPostingReq);
        return new BaseResponse<String>("");
    }
}
