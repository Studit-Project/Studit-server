package com.example.studit.service;

import com.example.studit.domain.User.User;
import com.example.studit.domain.enumType.Category;
import com.example.studit.domain.enumType.StudyStatus;
import com.example.studit.domain.invitation.Invitation;
import com.example.studit.domain.notification.NotificationType;
import com.example.studit.domain.study.ParticipatedStudy;
import com.example.studit.domain.study.Study;
import com.example.studit.domain.study.dto.GetInteriorRes;
import com.example.studit.domain.study.dto.PatchAddReq;
import com.example.studit.domain.study.dto.PostCreateReq;
import com.example.studit.domain.study.dto.GetManageRes;
import com.example.studit.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
    private final ChallengeRepository challengeRepository;

    @Autowired
    private final ParticipatedStudyRepository participatedStudyRepository;

    @Autowired
    private final UserService userService;

    @Autowired
    private final InvitationRepository invitationRepository;

    private final NotificationService notificationService;

    private final FCMService fcmService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**스터디룸 개설**/
    public Long save(PostCreateReq studyCreateReq){

        //현재 로그인한 유저 정보
        User user = userService.getUserFromAuth();


        Study study = new Study(studyCreateReq);
        study.addLeader(user);

//        ParticipatedStudy participatedStudy = new ParticipatedStudy(user, study);

        Long count = studyRepository.countByLeader(user) + challengeRepository.countByUser(user);
        user.levelUp(count);

        //user 무조건 업데이트 되어야 하므로 null 체크 삭제
//        participatedStudyRepository.save(participatedStudy);
        studyRepository.save(study);
//        userRepository.save(user);

        return study.getId();
    }

    /**개인이 참여한 스터디 목록**/
    public List<GetManageRes> findIndividualStudies() {
        User user = userService.getUserFromAuth();

        List<Study> studies = user.getStudies();

        List<ParticipatedStudy> participatedStudies = user.getParticipatedStudies(); //내가 참여한 스터디 리스트

        List<GetManageRes> getManageRes = studies.stream()
                .map(GetManageRes::new)
                .collect(Collectors.toList());


        List<GetManageRes> getManageResParticipated = participatedStudies.stream()
                        .map(GetManageRes::new)
                        .collect(Collectors.toList());

        for(GetManageRes getManageRes1 : getManageResParticipated) {
            getManageRes.add(getManageRes1);
        }

        return getManageRes;
    }

    /**스터디원 초대**/
    public Long inviteFollower(Long studyId, PatchAddReq nickname) throws IOException {
        Optional<User> user = userRepository.findByNickname(nickname.getNickname());
        Optional<Study> study = studyRepository.findById(studyId);

        //중복 처리
        Optional<Invitation> entity = invitationRepository.findByStudyAndUser(study.get(), user.get());

        if(entity.isEmpty()){
            Invitation invitation = new Invitation(user.get(), study.get());
            invitationRepository.save(invitation);

            user.get().addInvitation(invitation);
            study.get().addInvitation(invitation);

            //notificationService.send(user.get(), NotificationType.INVITE, "새로운 초대 요청이 도착했습니다.", "");

            fcmService.sendMessageToUser(NotificationType.INVITE.getMessage(), study.get().getName() + "...", user.get().getId());

            return invitation.getId();
        } else{
            System.out.println("이미 초대함");
            return entity.get().getId();
        }
    }



    /**스터디원 추가**/
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

    /**스터디룸 내부**/
    public GetInteriorRes getOne(Long studyId) {
        Optional<Study> study = studyRepository.findById(studyId);

        GetInteriorRes studyRoom = new GetInteriorRes(study.get());
        return studyRoom;
    }

    /**스터디원 강퇴**/
    public void expelFollower(Long studyId, Long followerId) throws IOException {
        Optional<Study> study = studyRepository.findById(studyId);
        Optional<User> user = userRepository.findById(followerId);

        study.get().getParticipatedMembers().removeIf(p -> p.getUser().getId() == followerId);

        ParticipatedStudy participatedStudy = participatedStudyRepository.findByUserAndStudy(user.get(), study.get());
        participatedStudyRepository.delete(participatedStudy);

        fcmService.sendMessageToUser(study.get().getName() + NotificationType.EXPEL.getMessage(),
                study.get().getName() + "...", user.get().getId());

        //notificationService.send(user.get(), NotificationType.EXPEL, studyId + "에서 강퇴당하셨습니다.", "");

    }

    /**스터디 삭제**/
    public void delete(Long studyId) {
        Optional<Study> study = studyRepository.findById(studyId);
        study.get().deleteStudy();

        List<ParticipatedStudy> participatedStudies = participatedStudyRepository.findByStudy(study.get());

        for(ParticipatedStudy participatedStudy : participatedStudies) {
            participatedStudy.delete();
        }

    }

    /**초대 승낙 여부**/
    public void accept(Long invitationId, boolean acceptance) throws IOException {
        User user = userService.getUserFromAuth();
        Optional<Invitation> invitation = invitationRepository.findById(invitationId);
        Optional<Study> study = studyRepository.findById(invitation.get().getStudy().getId());

        if(acceptance == true){
            ParticipatedStudy participatedStudy = new ParticipatedStudy();
            participatedStudy.addUser(user);
            study.get().addOne(participatedStudy);

            fcmService.sendMessageToUser(user.getNickname() + NotificationType.ACCEPT.getMessage(),
                    study.get().getName() + "...", study.get().getLeader().getId());


            //notificationService.send(study.get().getLeader(), NotificationType.ACCEPT, user.getNickname() + "님이 초대를 수락하셨습니다.", "");

        } else {
            fcmService.sendMessageToUser(user.getNickname() + NotificationType.REFUSE.getMessage(),
                    study.get().getName() + "...", study.get().getLeader().getId());

            //notificationService.send(study.get().getLeader(), NotificationType.REFUSE, user.getNickname() + "님이 초대를 거절하셨습니다.", "");
        }

        invitationRepository.delete(invitation.get());

    }

    /** 스터디 상태 업데이트 **/
    public void updateStatus(Long studyId, StudyStatus studyStatus) {
        Optional<Study> study = studyRepository.findById(studyId);
        study.get().updateStudyStatus(studyStatus);
    }

    /** 스터디 나가기 **/
    public String leaveStudy(Long studyId) {
        User user = userService.getUserFromAuth();
        Optional<Study> study = studyRepository.findById(studyId);

        ParticipatedStudy participatedStudy = participatedStudyRepository.findByUserAndStudy(user, study.get());
        participatedStudyRepository.delete(participatedStudy);
        return "스터디에서 나가기 성공";
    }
}
