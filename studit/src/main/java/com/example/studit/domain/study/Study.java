package com.example.studit.domain.study;

import com.example.studit.domain.Status;
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

    //회장
    @OneToOne(mappedBy = "study", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MyStudy leader;

    //스터디원
    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<ParticipatedStudy> participatedMembers = new ArrayList<>();
}
