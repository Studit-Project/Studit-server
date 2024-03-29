package com.example.studit.domain.study.dto;

import com.example.studit.domain.study.Activity;
import com.example.studit.domain.study.ParticipatedStudy;
import com.example.studit.domain.study.Study;
import com.example.studit.domain.User.dto.UserInfoDto;
import lombok.*;

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
        this.leader = study.getLeader().toUserInfoDto();
        this.participatedMembers = study.getParticipatedMembers()
                .stream().map(UserInfoDto::new)
                .collect(Collectors.toList());
    }


    public GetManageRes(ParticipatedStudy participatedStudy){
        this.id = participatedStudy.getStudy().getId();
        this.name = participatedStudy.getStudy().getName();
        this.number = participatedStudy.getStudy().getParticipatedMembers().size() + 1;
        this.currentNum = participatedStudy.getStudy().getParticipatedMembers().size() + 1;
        this.activity = participatedStudy.getStudy().getActivity();
        this.leader = participatedStudy.getStudy().getLeader().toUserInfoDto();
        this.participatedMembers = participatedStudy.getStudy().getParticipatedMembers()
                .stream().map(UserInfoDto::new)
                .collect(Collectors.toList());
    }

}
