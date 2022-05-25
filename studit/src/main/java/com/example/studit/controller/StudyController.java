package com.example.studit.controller;

import com.example.studit.config.swagger.BaseResponse;
import com.example.studit.dto.StudyCreateDto;
import com.example.studit.dto.StudyManageDto;
import com.example.studit.service.StudyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudyController {

    @Autowired
    private final StudyService studyService;

    //스터디룸 개설
    @ApiOperation("스터디 방 개설")
    @PostMapping("/study/management/new")
    public BaseResponse<Long> createStudy(@RequestBody StudyCreateDto studyCreateDto){
        return new BaseResponse<>(studyService.save(studyCreateDto));
    }

    //스터디 관리
    @ApiOperation("스터디 관리 페이지")
    @GetMapping("/study/management")
    public BaseResponse<List<StudyManageDto>> StudyManage(){
        return new BaseResponse<List<StudyManageDto>>(studyService.findIndividualStudies());
    }

    //스터디원 추가
    @ApiOperation("스터디원 추가")
    @PatchMapping("/study/{studyId}")
    public BaseResponse<Long> AddStudyOne(@PathVariable("studyId")  Long studyId, @RequestBody String nickname){
        return new BaseResponse<Long>(studyService.addStudyOne(studyId, nickname));
    }
}
