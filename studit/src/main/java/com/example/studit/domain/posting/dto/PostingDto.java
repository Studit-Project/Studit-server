package com.example.studit.domain.posting.dto;

import com.example.studit.domain.comment.dto.CommentResponseDto;
import com.example.studit.domain.enumType.Gender;
import com.example.studit.domain.enumType.Target;
import com.example.studit.domain.posting.Posting;
import com.example.studit.domain.enumType.Category;
import com.example.studit.domain.User.dto.UserInfoDto;
import com.example.studit.domain.posting.Province;
import com.example.studit.domain.study.Activity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PostingDto {

    private Long id;

    private Category category;

    private String title;

    private UserInfoDto userInfoDto;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime localDateTime;

    private String content;

    private Province province;
    private Activity activity;
    private Target target;
    private Gender gender;

    private List<CommentResponseDto> commentList;

    @Builder
    public PostingDto(Posting posting){
        this.id = posting.getId();
        this.category = posting.getCategory();
        this.title = posting.getTitle();
        this.userInfoDto = posting.getUser().toUserInfoDto();
        this.localDateTime = posting.getUpdatedAt();
        this.content = posting.getContent();
        this.province = posting.getProvince();
        this.activity = posting.getActivity();
        this.target = posting.getTarget();
        this.gender = posting.getGender();
        this.commentList = posting.getComments().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
