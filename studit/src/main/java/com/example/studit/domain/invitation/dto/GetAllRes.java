package com.example.studit.domain.invitation.dto;

import com.example.studit.domain.invitation.Invitation;
import com.example.studit.domain.study.dto.GetBasicRes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllRes {
    private Long id;
    private GetBasicRes study;

    public GetAllRes(Invitation invitation){
        this.id = invitation.getId();
        this.study = new GetBasicRes(invitation.getStudy());
    }
}
