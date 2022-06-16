package com.example.studit.domain.study;

import com.example.studit.domain.BaseEntity;
import com.example.studit.domain.bulletin.BulletinBoard;
import com.example.studit.domain.enumType.StudyStatus;
import com.example.studit.domain.enumType.Target;
import com.example.studit.domain.invitation.Invitation;
import com.example.studit.domain.posting.Province;
import com.example.studit.domain.study.dto.PostCreateReq;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    //회장
    @OneToOne(mappedBy = "study", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MyStudy leader;

    //스터디원
    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<ParticipatedStudy> participatedMembers = new ArrayList<>();

    //초대한 유저들
    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<Invitation> invitations = new ArrayList<>();

    //내부 게시판
    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<BulletinBoard> bulletinBoards = new ArrayList<>();

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
    public void addLeader(MyStudy leader){
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
}
