package com.example.studit.domain.bulletin.dto;

import com.example.studit.domain.User.dto.UserInfoDto;
import com.example.studit.domain.bulletin.BulletinBoard;
import com.example.studit.domain.comment.dto.CommentRequestDto;
import com.example.studit.domain.comment.dto.CommentResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class GetDetailRes {
    private Long id;
    private UserInfoDto userInfoDto;
    private String title;
    private LocalDateTime createAt;
    private String content;
    private List<CommentResponseDto> comments;

    public GetDetailRes(BulletinBoard bulletinBoard){
        this.id = bulletinBoard.getId();
        this.userInfoDto = bulletinBoard.getUser().toUserInfoDto();
        this.title = bulletinBoard.getTitle();
        this.createAt = bulletinBoard.getCreatedAt();
        this.content = bulletinBoard.getContent();
        this.comments = bulletinBoard.getComments().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
