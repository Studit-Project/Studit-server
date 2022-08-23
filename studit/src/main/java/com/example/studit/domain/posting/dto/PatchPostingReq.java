package com.example.studit.domain.posting.dto;

import com.example.studit.domain.enumType.Category;
import com.example.studit.domain.enumType.Gender;
import com.example.studit.domain.enumType.StudyStatus;
import com.example.studit.domain.enumType.Target;
import com.example.studit.domain.posting.Field;
import com.example.studit.domain.posting.Province;
import com.example.studit.domain.study.Activity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchPostingReq {
    private Category category;
    private Activity activity;
    private Target target;
    private Gender gender;
    private StudyStatus studyStatus;
    private Province province;
    private String title;
    private String content;
    private Field field;
}
