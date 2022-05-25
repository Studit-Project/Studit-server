package com.example.studit.domain.study.dto;

import com.example.studit.domain.study.Activity;
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

    private String introduction;

    private int number;

    private int currentNum;

    private UserInfoDto leader;

    private Activity activity;

    private List<UserInfoDto> participatedMembers = new ArrayList<>();

    public GetManageRes(Study study){
        this.id = study.getId();
        this.name = study.getName();
        this.introduction = study.getIntroduction();
        this.number = study.getNumber();
        this.currentNum = study.getParticipatedMembers().size() + 1;
        this.activity = study.getActivity();
        this.participatedMembers = study.getParticipatedMembers()
                .stream().map(UserInfoDto::new)
                .collect(Collectors.toList());
    }

//    @Builder
//    public StudyManageDto( String name, String introduction, int number, int currentNum, UserInfoDto leader, Activity activity, List<UserInfoDto> participatedMembers){
////        this.id = id;
//        this.name = name;
//        this.introduction = introduction;
//        this.number = number;
//        this.currentNum = currentNum;
//        this.leader = leader;
//        this.participatedMembers = participatedMembers;
//    }


}
