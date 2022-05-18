package com.example.studit.dto;

import com.example.studit.domain.Posting;
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
    public PostingListDto(Long id, String title, Long userId, LocalDateTime localDateTime){
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.localDateTime = localDateTime;
    }
}
