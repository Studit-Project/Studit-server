package com.example.studit.domain.posting.dto;

import com.example.studit.domain.enumType.Category;
import com.example.studit.domain.posting.Posting;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetProfileDto {
    private Long id;
    private Category category;
    private String content;
    private int likes;
    private int comments;

    public GetProfileDto(Posting posting){
        this.id = posting.getId();
        this.category = posting.getCategory();
        this.content = posting.getContent();
        this.likes = posting.getLikes().size();
        this.comments = posting.getComments().size();
    }
}
