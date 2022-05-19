package com.example.studit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;
    private Long userId;
    private String content;
    private LocalDateTime localDateTime;

    @Builder
    public CommentResponseDto(Long id, Long userId, String content, LocalDateTime localDateTime){
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.localDateTime = localDateTime;
    }
}
