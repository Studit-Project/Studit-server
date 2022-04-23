package com.example.studit.controller;

import com.example.studit.domain.User;
import com.example.studit.domain.study.Study;
import com.example.studit.dto.StudyCreateDto;
import com.example.studit.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudyController {

    @Autowired
    private final StudyService studyService;

    //스터디룸 개설
    @PostMapping("/study/create")
    public Long StudyCreate(StudyCreateDto studyCreateDto){
        return studyService.save(studyCreateDto);
    }


}
