package com.example.studit.dto;

import com.example.studit.domain.User.User;
import lombok.*;

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
    @Builder
    public UserJoinDto(String userName, String phone, String password, String email){
        this.userName = userName;
        this.phone = phone;
        this.password = password;
        this.email = email;
    }
}
