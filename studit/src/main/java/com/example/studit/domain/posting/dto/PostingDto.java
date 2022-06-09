package com.example.studit.domain.posting.dto;

import com.example.studit.domain.comment.dto.CommentResponseDto;
import com.example.studit.domain.posting.Posting;
import com.example.studit.domain.enumType.Category;
import com.example.studit.domain.User.dto.UserInfoDto;
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

    private LocalDateTime localDateTime = LocalDateTime.now();

    private String content;

    private List<CommentResponseDto> commentList;

    @Builder
    public PostingDto(Posting posting){
        this.id = posting.getId();
        this.category = posting.getCategory();
        this.title = posting.getTitle();
        this.userInfoDto = posting.getUser().toUserInfoDto();
        this.localDateTime = posting.getLocalDateTime();
        this.content = posting.getContent();
        this.commentList = posting.getComments().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
