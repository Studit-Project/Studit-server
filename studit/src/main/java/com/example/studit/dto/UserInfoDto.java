package com.example.studit.dto;

import com.example.studit.domain.User;
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
    private String email;

    @Builder
    public UserInfoDto(User user){
        this.id = user.getId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
    }
}
