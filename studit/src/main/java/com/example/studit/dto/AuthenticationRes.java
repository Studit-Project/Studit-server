package com.example.studit.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRes {
    String num;

    public AuthenticationRes(String num) {
        this.num = num;
    }
}
