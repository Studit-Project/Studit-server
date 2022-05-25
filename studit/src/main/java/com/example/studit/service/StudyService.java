package com.example.studit.service;

import com.example.studit.domain.User;
import com.example.studit.domain.study.MyStudy;
import com.example.studit.domain.study.ParticipatedStudy;
import com.example.studit.domain.study.Study;
import com.example.studit.dto.StudyCreateDto;
import com.example.studit.dto.StudyManageDto;
import com.example.studit.repository.MyStudyRepository;
import com.example.studit.repository.ParticipatedStudyRepository;
import com.example.studit.repository.StudyRepository;
import com.example.studit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final StudyRepository studyRepository;

    @Autowired
    private final MyStudyRepository myStudyRepository;

    @Autowired
    private final ParticipatedStudyRepository participatedStudyRepository;

    @Autowired
    private final UserService userService;

    /*스터디룸 개설*/
    public Long save(StudyCreateDto studyCreateDto){

        //현재 로그인한 유저 정보
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
        User user = userRepository.findByUserName(authentication.getName());

        MyStudy myStudy = new MyStudy();
        myStudy.addUser(user);

        Study study = studyRepository.save(studyCreateDto.toEntity());

        myStudyRepository.save(myStudy);
        study.addLeader(myStudy);

        return study.getId();
    }

    /*개인이 참여한 스터디 목록*/
    public List<StudyManageDto> findIndividualStudies() {
        User user = userService.getUserFromAuth();


        List<Study> studies = new ArrayList<>();
        List<MyStudy> myStudies = user.getMyStudies(); //내가 리더인 스터디 리스트
        List<ParticipatedStudy> participatedStudies = user.getParticipatedStudies(); //내가 참여한 스터디 리스트

        Set<Long> myStudyId = new HashSet<>();
        Set<Long> participatedStudyId = new HashSet<>();

        if(!myStudies.isEmpty()){
            for(int i = 0; i < myStudies.size(); i++) {
                myStudyId = Set.of(myStudies.get(i).getId());
            }
        }

        if(!participatedStudies.isEmpty()){
            for(int i = 0; i <= myStudies.size(); i++)
                participatedStudyId = Set.of(participatedStudies.get(i).getId());
        }

        studies.addAll(studyRepository.findAllByLeaderIdIn(myStudyId));
        studies.addAll(studyRepository.findAllByParticipatedMembersIdIn(participatedStudyId));

        List<StudyManageDto> studyManageDtos = studies.stream()
                .map(StudyManageDto::new)
                .collect(Collectors.toList());

        return studyManageDtos;
    }

    /*스터디원 추가*/
    public Long addStudyOne(Long studyId, String nickname) {
        User user = userService.getUserFromAuth();
        Optional<Study> study = studyRepository.findById(studyId);
        User leader = study.get().getLeader().getUser();
        ParticipatedStudy participatedStudy = new ParticipatedStudy();

        if(user == leader){
            //참여한 스터디 추가
            participatedStudy.addUser(userRepository.findUserByNickname(nickname));
            participatedStudyRepository.save(participatedStudy);

            //스터디에 추가
            study.get().addOne(participatedStudy);
        }

        return participatedStudy.getId();
    }

}
