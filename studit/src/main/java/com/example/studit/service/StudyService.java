package com.example.studit.service;

import com.example.studit.domain.User;
import com.example.studit.domain.study.MyStudy;
import com.example.studit.domain.study.Study;
import com.example.studit.dto.StudyCreateDto;
import com.example.studit.repository.MyStudyRepository;
import com.example.studit.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyService {

    @Autowired
    private final StudyRepository studyRepository;

    @Autowired
    private final MyStudyRepository myStudyRepository;

    //스터디룸 개설
    public Long save(StudyCreateDto studyCreateDto){

        //현재 로그인한 유저 정보
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        MyStudy myStudy = new MyStudy();
        myStudy.setUser(user);

        Study study = studyRepository.save(studyCreateDto.toEntity());

        myStudyRepository.save(myStudy);
        study.setUser(myStudy);

        return study.getId();
    }
}
