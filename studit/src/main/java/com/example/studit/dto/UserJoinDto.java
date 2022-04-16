package com.example.studit.dto;

import com.example.studit.domain.User;
import lombok.*;

import javax.persistence.GeneratedValue;
import java.util.Collections;

@Getter @Setter
@NoArgsConstructor
public class UserJoinDto {
    private String userName;
    private String phone;
    private String password;
    private String email;
    private String auth;

    public User toEntity(){
        return User.builder()
                .userName(userName)
                .phone(phone)
                .pwd(password)
                .email(email)
                .build();
    }
    @Builder
    public UserJoinDto(String userName, String phone, String password, String email){
        this.userName = userName;
        this.phone = phone;
        this.password = password;
        this.email = email;
    }
}
