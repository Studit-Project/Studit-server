package com.example.studit.domain.study;

import com.example.studit.domain.Status;
import com.example.studit.domain.User;
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

    //회장
    @OneToOne(mappedBy = "study", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MyStudy leader;

    //스터디원
    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<ParticipatedStudy> participatedMembers = new ArrayList<>();

    @Builder
    public Study(String province, String city, String district, Target target){
        this.region = new Region(province, city, district);
        this.target = target;
    }

    //연관관계 메서드
    public void setUser(MyStudy leader){
        this.leader = leader;
        leader.setStudy(this);
    }

}
