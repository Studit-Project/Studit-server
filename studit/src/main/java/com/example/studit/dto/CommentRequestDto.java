package com.example.studit.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentRequestDto {
    private String content;
//    private LocalDateTime localDateTime = LocalDateTime.now();
}
