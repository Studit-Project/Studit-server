package com.example.studit.domain.study.dto;

import com.example.studit.domain.study.Activity;
import com.example.studit.domain.User.dto.UserInfoDto;
import com.example.studit.domain.study.Study;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetBasicRes {
    private Long id;

    //스터디명
    private String name;

    private String introduction;

    private int number;

    private int currentNum;

    private UserInfoDto leader;

    private Activity activity;

    public GetBasicRes(Study study){
        this.id = study.getId();
        this.introduction = study.getIntroduction();
        this.name = study.getName();
        this.number = study.getNumber();
        this.currentNum = study.getParticipatedMembers().size() + 1;
        this.leader = study.getLeader().getUser().toUserInfoDto();
        this.activity = study.getActivity();
    }
}
