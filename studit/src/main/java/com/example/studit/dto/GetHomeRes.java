package com.example.studit.dto;

import com.example.studit.domain.User.User;
import com.example.studit.domain.challenge.dto.GetHighestRes;
import com.example.studit.domain.study.dto.GetHomeStudiesRes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetHomeRes {
    private String nickname; //user
    private GetHighestRes challenge;
    private List<GetHomeStudiesRes> studies;

    public GetHomeRes(User user, List<GetHomeStudiesRes> studies){
        this.nickname = user.getNickname();
        this.studies = studies;
    }
}
