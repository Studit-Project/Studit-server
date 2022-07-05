package com.example.studit.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequestDto {
    private String identity;
    private String password;
}
