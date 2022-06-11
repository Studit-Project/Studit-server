package com.example.studit.controller;

import com.example.studit.config.swagger.BaseResponse;
import com.example.studit.domain.study.dto.GetInteriorRes;
import com.example.studit.domain.study.dto.PatchAddReq;
import com.example.studit.domain.study.dto.PostCreateReq;
import com.example.studit.domain.study.dto.GetManageRes;
import com.example.studit.service.StudyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyController {

    @Autowired
    private final StudyService studyService;

    //스터디룸 개설
    @ApiOperation("스터디 방 개설")
    @PostMapping("/management/new")
    public BaseResponse<Long> createStudy(@RequestBody PostCreateReq studyCreateReq){
        return new BaseResponse<>(studyService.save(studyCreateReq));
    }

    //스터디 관리
    @ApiOperation("스터디 관리 페이지")
    @GetMapping("/management")
    public BaseResponse<List<GetManageRes>> studyManage(){
        return new BaseResponse<List<GetManageRes>>(studyService.findIndividualStudies());
    }

    //스터디룸
    @ApiOperation("스터디룸")
    @GetMapping("/{studyId}")
    public BaseResponse<GetInteriorRes> getStudyRoom(@PathVariable("studyId") Long studyId){
        return new BaseResponse<GetInteriorRes>(studyService.getOne(studyId));
    }

    //스터디원 초대
    @ApiOperation("스터디원 초대")
    @PostMapping("/{studyId}/recruitment")
    public BaseResponse<String> inviteFollower(@PathVariable(name = "studyId") Long studyId, @RequestBody PatchAddReq nickname){
        studyService.inviteFollower(studyId, nickname);
        return new BaseResponse<String>("");
    }

    //스터디원 추가
    @ApiOperation("스터디원 추가")
    @PatchMapping("/{studyId}")
    public BaseResponse<Long> addStudyOne(@PathVariable("studyId") Long studyId, @RequestBody PatchAddReq patchAddReq){
        return new BaseResponse<Long>(studyService.addStudyOne(studyId, patchAddReq));
    }

    //스터디원 강퇴
    @ApiOperation("강퇴")
    @PatchMapping("/{studyId}/expulsion/{followerId}")
    public BaseResponse<String> expel(@PathVariable(name = "studyId") Long studyId, @PathVariable(name = "followerId") Long followerId){
        studyService.expelFollower(studyId, followerId);
        return new BaseResponse<String>("");
    }
}
