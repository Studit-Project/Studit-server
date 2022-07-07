package com.example.studit.domain.User.dto;

import com.example.studit.domain.User.User;
import com.example.studit.domain.badge.dto.GetProfileRes;
import com.example.studit.domain.posting.dto.GetProfileDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProfileDto {
    private Long id;

    private String identity;

    private String nickname;
    private String statusMessage;
    //뱃지 리스트
    private List<GetProfileRes> badges;
    //글 리스트
    private List<GetProfileDto> postings;

    public ProfileDto(User user){
        this.id = user.getId();
        this.identity = user.getIdentity();
        this.statusMessage = user.getStatusMessage();
        this.nickname = user.getNickname();
        this.badges = user.getBadges().stream()
                .map(GetProfileRes::new)
                .collect(Collectors.toList());
        this.postings = user.getPostings().stream()
                .map(GetProfileDto::new)
                .collect(Collectors.toList());
    }
}
