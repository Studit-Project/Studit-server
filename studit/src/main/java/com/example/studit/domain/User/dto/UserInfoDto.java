package com.example.studit.domain.User.dto;

import com.example.studit.domain.User.User;
import com.example.studit.domain.study.ParticipatedStudy;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoDto {
    private Long id;
    private String userName;
    private String phone;
    private String email;
    private String nickname;

    @Builder
    public UserInfoDto(User user){
        this.id = user.getId();
        this.userName = user.getUserName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.nickname = user.getNickname();

    }

    public UserInfoDto(ParticipatedStudy participatedStudy){
        this.id = participatedStudy.getId();
        this.userName = participatedStudy.getUser().getUserName();
        this.phone = participatedStudy.getUser().getPhone();
        this.email = participatedStudy.getUser().getEmail();
        this.nickname = participatedStudy.getUser().getNickname();
    }
}
