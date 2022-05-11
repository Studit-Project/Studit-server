package com.example.studit.controller;

import com.example.studit.dto.StudyCreateDto;
import com.example.studit.dto.StudyManageDto;
import com.example.studit.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudyController {

    @Autowired
    private final StudyService studyService;

    //스터디룸 개설
    @PostMapping("/study/manage/create")
    public Long StudyCreate(@RequestBody StudyCreateDto studyCreateDto){
        return studyService.save(studyCreateDto);
    }

    //스터디 관리
    @GetMapping("/study/manage")
    public List<StudyManageDto> StudyManage(){
        return studyService.findIndividualStudies();
    }
}
