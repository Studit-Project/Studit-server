package com.example.studit.domain.study;

import com.example.studit.domain.BaseEntity;
import com.example.studit.domain.Status;
import com.example.studit.domain.User.User;
import com.example.studit.domain.bulletin.BulletinBoard;
import com.example.studit.domain.enumType.StudyStatus;
import com.example.studit.domain.enumType.Target;
import com.example.studit.domain.invitation.Invitation;
import com.example.studit.domain.posting.Province;
import com.example.studit.domain.study.dto.PostCreateReq;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Study {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id")
    private Long id;

    //스터디명
    private String name;

    //스터디 소개
    private String introduction;

    //모집 상태
    @Enumerated(EnumType.STRING)
    private StudyStatus studyStatus = StudyStatus.RECRUITING;

    //지역
    @Embedded
    private Region region;

    //모집 대상
    @Enumerated(EnumType.STRING)
    private Target target;

    //모집 인원
    private int number;

    //활동
    @Enumerated(EnumType.STRING)
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User leader;


    //스터디원
    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<ParticipatedStudy> participatedMembers = new ArrayList<>();

    //초대한 유저들
    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<Invitation> invitations = new ArrayList<>();

    //내부 게시판
    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<BulletinBoard> bulletinBoards = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, columnDefinition = "varchar(10) default 'ACTIVE'")
    private Status entityStatus = Status.ACTIVE;

    //공지사항
    private String announcement;

    public void changeStatus(Status status) {
        this.entityStatus = status;
    }

    @Builder
    public Study(Province province, String city, String district, Target target, int number, Activity activity){
        this.region = new Region(province, city, district);
        this.target = target;
        this.number = number;
        this.activity = activity;
    }

    public Study(PostCreateReq postCreateReq){
        this.name = postCreateReq.getName();
        this.introduction = postCreateReq.getIntroduction();
        this.region = new Region(postCreateReq.getProvince(), postCreateReq.getCity(), postCreateReq.getDistrict());
        this.target = postCreateReq.getTarget();
        this.number = postCreateReq.getNumber();
        this.activity = postCreateReq.getActivity();
    }

    //연관관계 메서드
    //리더 지정
    public void addLeader(User leader){
        this.leader = leader;
        leader.addStudy(this);
    }

    //스터디원 추가
    public void addOne(ParticipatedStudy participatedStudy){
        participatedMembers.add(participatedStudy);
        participatedStudy.addStudy(this);
    }

    //초대
    public void addInvitation(Invitation invitation){
        invitations.add(invitation);
    }
    public void post(BulletinBoard bulletinBoard){
        bulletinBoards.add(bulletinBoard);
    }

    //공지사항
    public void updateAnnouncement(String announcement){
        this.announcement = announcement;
    }

    //모집 상태 변경
    public void updateStudyStatus(StudyStatus studyStatus) {
        this.studyStatus = studyStatus;
    }

    public void deleteStudy(){
        changeStatus(Status.DELETED);
    }
}
