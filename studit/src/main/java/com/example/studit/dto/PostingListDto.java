package com.example.studit.dto;

import com.example.studit.domain.posting.Posting;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostingListDto {
    private Long id;
    private String title;
    private Long userId;
    private LocalDateTime localDateTime;

    @Builder
    public PostingListDto(Posting posting){
        this.id = posting.getId();
        this.title = posting.getTitle();
        this.userId = posting.getUser().getId();
        this.localDateTime = posting.getLocalDateTime();
    }
}
