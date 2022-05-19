package com.example.studit.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentRequestDto {
    private Long id;
    private Long postingId;
    private String content;
    private UserInfoDto userInfoDto;
    private LocalDateTime localDateTime = LocalDateTime.now();
}
