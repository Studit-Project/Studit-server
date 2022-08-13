package com.example.studit.domain.challenge.dto;

import com.example.studit.domain.Image.Image;
import com.example.studit.domain.challenge.Challenge;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeDto {
    private Long id;
    private String title;
    private String content;
    private String subject;
    private String status;
    private String createdate;
    private List<String> imgUrl;
    private Long userId;
    private String nickName;
    private String profileUrl;

    public ChallengeDto makeResponse(Challenge challenge, List<String> imgs){
        return ChallengeDto.builder()
                .id(challenge.getId())
                .title(challenge.getTitle())
                .content(challenge.getContent())
                .subject(challenge.getSubject().toString())
                //.status(challenge.getChallengeStatus().toString())
                .userId(challenge.getUser().getId())
                .nickName(challenge.getUser().getNickname())
                .imgUrl(imgs)
                .build();
    }
}
