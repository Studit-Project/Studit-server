package com.example.studit.domain.study;

import com.example.studit.domain.enumType.Status;
import com.example.studit.domain.enumType.Target;
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
    private Status status;

    //지역
    @Embedded
    private Region region;

    //모집 대상
    @Enumerated(EnumType.STRING)
    private Target target;

    //모집 인원
    private int number;

    //현재 인원
    private int currentNum;

    //활동
    @Enumerated(EnumType.STRING)
    private Activity activity;

    //회장
    @OneToOne(mappedBy = "study", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MyStudy leader;

    //스터디원
    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<ParticipatedStudy> participatedMembers = new ArrayList<>();

    @Builder
    public Study(String province, String city, String district, Target target, int number, Activity activity){
        this.region = new Region(province, city, district);
        this.target = target;
        this.number = number;
        this.activity = activity;
    }

    //연관관계 메서드
    //리더 지정
    public void setUser(MyStudy leader){
        this.leader = leader;
        leader.setStudy(this);
    }
}
