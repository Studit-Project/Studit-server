package com.example.studit.domain.notification;

import lombok.Getter;

@Getter
public enum NotificationType {
    //ACCEPT == 스터디 초대 수락했을 때
    //REFUSE == 스터디 초대 거절했을 때
    //EXPEL == 스터디 강퇴당했을 때
    //LIKES == 내 글에 좋아요 눌렸을 때
    COMMENT("에 새로운 댓글이 달렸습니다."),
    ACCEPT("님이 초대를 수락하셨습니다."),
    REFUSE("님이 초대를 거절하셨습니다."),
    INVITE("새로운 초대 요청이 도착했습니다."),
    EXPEL("에서 강퇴당하셨습니다."),
    LIKES("님이 좋아요를 누르셨습니다.");

    private final String message;

    NotificationType(String message) {
        this.message = message;
    }
}
