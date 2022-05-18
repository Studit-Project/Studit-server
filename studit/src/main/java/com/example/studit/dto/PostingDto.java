package com.example.studit.dto;

import com.example.studit.domain.Category;
import com.example.studit.domain.Comment;
import com.example.studit.domain.Posting;
import com.example.studit.domain.User;
import lombok.Builder;
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

    private String title;

    private UserInfoDto userInfoDto;

    private LocalDateTime localDateTime = LocalDateTime.now();

    private String content;

    private List<CommentResponseDto> commentList = new ArrayList<>();

    @Builder
    public PostingDto(Long id, Category category, String title, UserInfoDto userInfoDto, LocalDateTime localDateTime, String content, List<CommentResponseDto> commentList){
        this.id = id;
        this.category = category;
        this.title = title;
        this.userInfoDto = userInfoDto;
        this.localDateTime = localDateTime;
        this.content = content;
        this.commentList = commentList;
    }
}
