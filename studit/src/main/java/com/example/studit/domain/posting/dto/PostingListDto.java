package com.example.studit.domain.posting.dto;

import com.example.studit.domain.enumType.StudyStatus;
import com.example.studit.domain.posting.Posting;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime localDateTime;
    private StudyStatus studyStatus;

    @Builder
    public PostingListDto(Posting posting){
        this.id = posting.getId();
        this.title = posting.getTitle();
        this.userId = posting.getUser().getId();
        this.localDateTime = posting.getUpdatedAt();
        this.studyStatus = posting.getStudyStatus();
    }
}
