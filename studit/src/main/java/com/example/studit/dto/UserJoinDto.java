package com.example.studit.dto;

import com.example.studit.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;

@Getter @Setter
@NoArgsConstructor
public class UserJoinDto {
    private String userName;
    private String phone;
    private String password;
    private String email;

    public User toEntity(){
        return User.builder()
                .userName(userName)
                .phone(phone)
                .pwd(password)
                .email(email)
                .build();
    }
}
