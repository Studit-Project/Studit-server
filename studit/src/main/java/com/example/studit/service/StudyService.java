package com.example.studit.service;

import com.example.studit.domain.User.User;
import com.example.studit.domain.invitation.Invitation;
import com.example.studit.domain.study.MyStudy;
import com.example.studit.domain.study.ParticipatedStudy;
import com.example.studit.domain.study.Study;
import com.example.studit.domain.study.dto.GetInteriorRes;
import com.example.studit.domain.study.dto.PatchAddReq;
import com.example.studit.domain.study.dto.PostCreateReq;
import com.example.studit.domain.study.dto.GetManageRes;
import com.example.studit.repository.MyStudyRepository;
import com.example.studit.repository.ParticipatedStudyRepository;
import com.example.studit.repository.StudyRepository;
import com.example.studit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Long save(PostCreateReq studyCreateReq){

        //현재 로그인한 유저 정보
        User user = userService.getUserFromAuth();

        MyStudy myStudy = new MyStudy();
        myStudy.addUser(user);

        Study study = new Study(studyCreateReq);

        studyRepository.save(study);
        myStudyRepository.save(myStudy);

        study.addLeader(myStudy);

        return study.getId();
    }

    /*개인이 참여한 스터디 목록*/
    public List<GetManageRes> findIndividualStudies() {
        User user = userService.getUserFromAuth();

        List<Study> studies = new ArrayList<>();
        List<MyStudy> myStudies = user.getMyStudies(); //내가 리더인 스터디 리스트
        List<ParticipatedStudy> participatedStudies = user.getParticipatedStudies(); //내가 참여한 스터디 리스트

        List<GetManageRes> getManageRes = myStudies.stream()
                .map(GetManageRes::new)
                .collect(Collectors.toList());

        List<GetManageRes> participated = participatedStudies.stream()
                        .map(GetManageRes::new)
                        .collect(Collectors.toList());

        getManageRes.addAll(participated);

        return getManageRes;
    }

    /*스터디원 초대*/
    public void inviteFollower(Long studyId, PatchAddReq nickname) {
        Optional<User> user = userRepository.findByNickname(nickname.getNickname());
        Optional<Study> study = studyRepository.findById(studyId);

        Invitation invitation = new Invitation(user.get(), study.get());
        user.get().addInvitation(invitation);
        study.get().addInvitation(invitation);
    }

    /*스터디원 추가*/
    public Long addStudyOne(Long studyId, PatchAddReq patchAddReq) {
        User user = userService.getUserFromAuth();
        Optional<Study> study = studyRepository.findById(studyId);

        ParticipatedStudy participatedStudy = new ParticipatedStudy();

        Optional<User> follower = userRepository.findByNickname(patchAddReq.getNickname());

        //참여한 스터디 추가
        participatedStudy.addUser(follower.get());
        System.out.println(follower + "!!!!!!!!!!!!!!!!!!!!!!!");
        participatedStudyRepository.save(participatedStudy);

        //스터디에 추가
        study.get().addOne(participatedStudy);

        return participatedStudy.getId();
    }

    /*스터디룸 내부**/
    public GetInteriorRes getOne(Long studyId) {
        Optional<Study> study = studyRepository.findById(studyId);

        GetInteriorRes studyRoom = new GetInteriorRes(study.get());
        return studyRoom;
    }

    /*스터디원 강퇴*/
    public void expelFollower(Long studyId, Long followerId) {
        Optional<Study> study = studyRepository.findById(studyId);
        Optional<User> user = userRepository.findById(followerId);

        study.get().getParticipatedMembers().removeIf(p -> p.getUser().getId() == followerId);

        ParticipatedStudy participatedStudy = participatedStudyRepository.findByUserAndStudy(user.get(), study.get());
        participatedStudyRepository.delete(participatedStudy);
    }

    /*스터디 삭제*/
    public void delete(Long studyId) {
        Optional<Study> study = studyRepository.findById(studyId);
        studyRepository.delete(study.get());
    }
}
