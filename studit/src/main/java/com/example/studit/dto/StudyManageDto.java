package com.example.studit.dto;

import com.example.studit.domain.study.Activity;
import com.example.studit.domain.study.ParticipatedStudy;
import com.example.studit.domain.study.Study;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudyManageDto {

//    private Long id;

    //스터디명
    private String name;

    private String introduction;

    private int number;

    private int currentNum;

    private UserInfoDto leader;

    private Activity activity;

    private List<UserInfoDto> participatedMembers = new ArrayList<>();

    @Builder
    public StudyManageDto( String name, String introduction, int number, int currentNum, UserInfoDto leader, Activity activity, List<UserInfoDto> participatedMembers){
//        this.id = id;
        this.name = name;
        this.introduction = introduction;
        this.number = number;
        this.currentNum = currentNum;
        this.leader = leader;
        this.participatedMembers = participatedMembers;
    }


}
