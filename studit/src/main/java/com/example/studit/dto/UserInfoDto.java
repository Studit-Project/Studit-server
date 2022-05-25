package com.example.studit.dto;

import com.example.studit.domain.User;
import com.example.studit.domain.study.ParticipatedStudy;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoDto {
    private Long id;
    private String userName;
    private String email;

    @Builder
    public UserInfoDto(User user){
        this.id = user.getId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
    }

    public UserInfoDto(ParticipatedStudy participatedStudy){
        this.id = participatedStudy.getId();
        this.userName = participatedStudy.getUser().getUserName();
        this.email = participatedStudy.getUser().getEmail();
    }
}
