package com.example.studit.domain.comment.dto;

import com.example.studit.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentResponseDto {
    private Long id;
    private Long userId;
    private String content;
    private LocalDateTime localDateTime;

    @Builder
    public CommentResponseDto(Comment comment){
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.localDateTime = localDateTime;
    }
}
