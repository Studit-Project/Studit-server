package com.example.studit.domain.User.dto;

import com.example.studit.domain.enumType.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatchDetailReq {
    private String number;
    private String nickname;
    private Gender gender;
    private LocalDate birth;
}
