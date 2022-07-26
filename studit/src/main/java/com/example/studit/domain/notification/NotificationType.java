package com.example.studit.domain.notification;

import lombok.Getter;

@Getter
public enum NotificationType {
    //ACCEPT == 스터디 초대 수락했을 때
    //REFUSE == 스터디 초대 거절했을 때
    //EXPEL == 스터디 강퇴당했을 때
    //LIKES == 내 글에 좋아요 눌렸을 때
    COMMENT("댓글이 달렸어요."),
    ACCEPT("스터디 초대 수락되었어요."),
    REFUSE("스터디 초대 거절되었어요."),
    INVITE("스터디에 초대되었어요."),
    EXPEL("스터디에서 강퇴당했어요."),
    LIKES("좋아요가 달렸어요.");

    private final String message;

    NotificationType(String message) {
        this.message = message;
    }
}
