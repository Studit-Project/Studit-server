package com.example.studit.domain.bulletin.dto;

import com.example.studit.domain.User.dto.UserInfoDto;
import com.example.studit.domain.bulletin.BulletinBoard;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetAllRes {
    private Long id;
    private UserInfoDto userInfoDto;
    private String title;
    private LocalDateTime createAt;

    public GetAllRes(BulletinBoard bulletinBoard){
        this.id = bulletinBoard.getId();
        this.title = bulletinBoard.getTitle();
        this.userInfoDto = bulletinBoard.getUser().toUserInfoDto();
        this.createAt = bulletinBoard.getCreateAt();
    }
}
