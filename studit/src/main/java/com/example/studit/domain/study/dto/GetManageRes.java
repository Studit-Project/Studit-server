package com.example.studit.domain.study.dto;

import com.example.studit.domain.study.Activity;
import com.example.studit.domain.study.MyStudy;
import com.example.studit.domain.study.ParticipatedStudy;
import com.example.studit.domain.study.Study;
import com.example.studit.dto.UserInfoDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class GetManageRes {

    private Long id;

    //스터디명
    private String name;

    private int number;

    private int currentNum;

    private UserInfoDto leader;

    private Activity activity;

    private List<UserInfoDto> participatedMembers;

    public GetManageRes(Study study){
        this.id = study.getId();
        this.name = study.getName();
        this.number = study.getNumber();
        this.currentNum = study.getParticipatedMembers().size() + 1;
        this.activity = study.getActivity();
        this.participatedMembers = study.getParticipatedMembers()
                .stream().map(UserInfoDto::new)
                .collect(Collectors.toList());
    }

    public GetManageRes(MyStudy myStudy){
        this.id = myStudy.getStudy().getId();
        this.name = myStudy.getStudy().getName();
        this.number = myStudy.getStudy().getNumber();
        this.currentNum = myStudy.getStudy().getParticipatedMembers().size() + 1;
        this.activity = myStudy.getStudy().getActivity();
        this.participatedMembers = myStudy.getStudy().getParticipatedMembers()
                .stream().map(UserInfoDto::new)
                .collect(Collectors.toList());
    }

    public GetManageRes(ParticipatedStudy participatedStudy){
        this.id = participatedStudy.getStudy().getId();
        this.name = participatedStudy.getStudy().getName();
        this.number = participatedStudy.getStudy().getParticipatedMembers().size() + 1;
        this.activity = participatedStudy.getStudy().getActivity();
        this.participatedMembers = participatedStudy.getStudy().getParticipatedMembers()
                .stream().map(UserInfoDto::new)
                .collect(Collectors.toList());
    }

}
