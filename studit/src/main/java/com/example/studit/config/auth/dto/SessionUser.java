package com.example.studit.config.auth.dto;


import com.example.studit.domain.User.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private Long id;
    private String name;
    private String email;

    private String nickname;

    private String age;

    private String phone;

    public SessionUser(User user) {
        this.id = user.getId();
        this.name = user.getUserName();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.age = user.getAgeRange();
        this.phone = user.getPhone();
    }

}
