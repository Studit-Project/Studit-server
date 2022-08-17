package com.example.studit.domain.posting.dto;

import com.example.studit.domain.enumType.Category;
import com.example.studit.domain.posting.Field;
import com.example.studit.domain.posting.Posting;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetProfileDto {
    private Long id;
    private Category category;
    private Field field;
    private String title;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime cratedAt;
    private int likes;
    private int comments;

    public GetProfileDto(Posting posting){
        this.id = posting.getId();
        this.category = posting.getCategory();
        this.field = posting.getField();
        this.title = posting.getTitle();
        this.content = posting.getContent();
        this.cratedAt = posting.getUpdatedAt();
        this.likes = posting.getLikes().size();
        this.comments = posting.getComments().size();
    }
}
