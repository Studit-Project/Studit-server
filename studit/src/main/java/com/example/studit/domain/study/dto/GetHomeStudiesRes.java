package com.example.studit.domain.study.dto;

import com.example.studit.domain.study.MyStudy;
import com.example.studit.domain.study.ParticipatedStudy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GetHomeStudiesRes {
    //스터디 id
    private Long id;
    //스터디 이름
    private String name;
    //스터디 설명
    private String introduction;
    //스터디 시작일
    private LocalDateTime createdAt;

    public GetHomeStudiesRes(MyStudy myStudy){
        this.id = myStudy.getStudy().getId();
        this.name = myStudy.getStudy().getName();
        this.introduction = myStudy.getStudy().getIntroduction();
        this.createdAt = myStudy.getCreatedAt();
    }

    public GetHomeStudiesRes(ParticipatedStudy participatedStudy){
        this.id = participatedStudy.getStudy().getId();
        this.name = participatedStudy.getStudy().getName();
        this.introduction = participatedStudy.getStudy().getIntroduction();
        this.createdAt = participatedStudy.getCreatedAt();
    }
}
