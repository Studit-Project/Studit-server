package com.example.studit.domain.User.dto;

import com.example.studit.domain.User.User;
import lombok.*;
import org.apache.commons.lang.RandomStringUtils;

@Getter @Setter
@NoArgsConstructor
public class UserJoinDto {

    private String identity;
    private String userName;
    private String phone;
    private String password;
    private String email;

    public User toEntity(){
        return User.builder()
                .identity(identity)
                .userName(userName)
                .phone(phone)
                .pwd(password)
                .email(email)
                //닉네임 초기값 랜덤 부여
                .nickname(RandomStringUtils.randomAlphanumeric(15).toUpperCase())
                .build();
    }
    @Builder
    public UserJoinDto(String identity, String userName, String phone, String password, String email){
        this.identity = identity;
        this.userName = userName;
        this.phone = phone;
        this.password = password;
        this.email = email;
    }
}
