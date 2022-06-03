package com.example.studit.domain.posting.dto;

import com.example.studit.domain.Status;
import com.example.studit.domain.enumType.Category;
import com.example.studit.domain.enumType.Gender;
import com.example.studit.domain.enumType.Target;
import com.example.studit.domain.posting.Province;
import com.example.studit.domain.study.Activity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreateReq {
    private Category category;
    private String title;
    private String content;
    private Province province;
    private Activity activity;
    private Target target;
    private Gender gender;
}
