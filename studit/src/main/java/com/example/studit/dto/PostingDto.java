package com.example.studit.dto;

import com.example.studit.domain.Category;
import com.example.studit.domain.Comment;
import com.example.studit.domain.Posting;
import com.example.studit.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostingDto {

    private Long id;

    private Category category;

    private UserInfoDto userInfoDto;

    private LocalDateTime localDateTime = LocalDateTime.now();

    private String content;

    private List<Comment> commentList = new ArrayList<>();



}
