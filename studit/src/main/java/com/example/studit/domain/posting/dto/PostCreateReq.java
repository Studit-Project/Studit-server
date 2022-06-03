package com.example.studit.domain.posting.dto;

import com.example.studit.domain.enumType.Category;
import com.example.studit.domain.posting.Province;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreateReq {
    private Category category;
    private String title;
    private String content;
    private Province province;
}
