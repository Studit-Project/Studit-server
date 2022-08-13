package com.example.studit.domain.study.dto;

import com.example.studit.domain.study.ParticipatedStudy;
import com.example.studit.domain.study.Study;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;


    public GetHomeStudiesRes(Study study){
        this.id = study.getId();
        this.name = study.getName();
        this.introduction = study.getIntroduction();
        this.createdAt = study.getCreatedDate();
    }

    public GetHomeStudiesRes(ParticipatedStudy participatedStudy){
        this.id = participatedStudy.getStudy().getId();
        this.name = participatedStudy.getStudy().getName();
        this.introduction = participatedStudy.getStudy().getIntroduction();
        this.createdAt = participatedStudy.getCreatedAt();
    }
}
