package com.example.studit.service;

import com.example.studit.domain.User.User;
import com.example.studit.domain.study.MyStudy;
import com.example.studit.domain.study.ParticipatedStudy;
import com.example.studit.domain.study.dto.GetHomeStudiesRes;
import com.example.studit.domain.study.dto.GetManageRes;
import com.example.studit.dto.GetHomeRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HomeService {

    private final UserService userService;

    public GetHomeRes getInfo() {
        User user = userService.getUserFromAuth();

        //가장 진행률 높은 챌린지 하나 가져오기(예정)

        /*스터디 리스트 가져오기*/
        List<MyStudy> myStudies = user.getMyStudies(); //내가 리더인 스터디 리스트
        List<ParticipatedStudy> participatedStudies = user.getParticipatedStudies(); //내가 참여한 스터디 리스트

        List<GetHomeStudiesRes> getHomeStudiesRes = myStudies.stream()
                .map(GetHomeStudiesRes::new)
                .collect(Collectors.toList());

        List<GetHomeStudiesRes> participated = participatedStudies.stream()
                .map(GetHomeStudiesRes::new)
                .collect(Collectors.toList());

        getHomeStudiesRes.addAll(participated);

        GetHomeRes getHomeRes = new GetHomeRes(user, getHomeStudiesRes);

        return getHomeRes;
    }
}
