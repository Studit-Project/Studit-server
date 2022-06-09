package com.example.studit.domain.badge.dto;

import com.example.studit.domain.badge.MyBadge;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetProfileRes {
    private Long id;
    private String name;

    public GetProfileRes(MyBadge myBadge){
        this.id = myBadge.getBadge().getId();
        this.name = myBadge.getBadge().getName();
    }
}
