package com.example.studit.domain.study.dto;

import com.example.studit.domain.User.dto.UserInfoDto;
import com.example.studit.domain.study.Study;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class GetInteriorRes {
    private Long id;
    private String name;
    private String introduction;
    private UserInfoDto leader;
    private List<UserInfoDto> followers;
    //공지사항
    private String announcement;

    public GetInteriorRes(Study study){
        this.id = study.getId();
        this.name = study.getName();
        this.introduction = study.getIntroduction();
        this.leader = study.getLeader().getUser().toUserInfoDto();
        this.followers = study.getParticipatedMembers().stream()
                .map(UserInfoDto::new)
                .collect(Collectors.toList());
        this.announcement = study.getAnnouncement();
    }
}
