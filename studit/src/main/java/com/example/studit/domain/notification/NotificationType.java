package com.example.studit.domain.notification;

public enum NotificationType {
    //ACCEPT == 스터디 초대 수락했을 때
    //REFUSE == 스터디 초대 거절했을 때
    //EXPEL == 스터디 강퇴당했을 때
    //LIKES == 내 글에 좋아요 눌렸을 때
    COMMENT, ACCEPT, REFUSE, INVITE, EXPEL, LIKES
}
