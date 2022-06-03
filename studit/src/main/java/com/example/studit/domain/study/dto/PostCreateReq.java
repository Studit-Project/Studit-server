package com.example.studit.domain.study.dto;

import com.example.studit.domain.posting.Province;
import com.example.studit.domain.study.Activity;
import com.example.studit.domain.study.Region;
import com.example.studit.domain.study.Study;
import com.example.studit.domain.enumType.Target;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreateReq {

    //스터디명
    private String name;

    //스터디 설명
    private String introduction;

    //지역
    private Province province;
    private String city;
    private String district;

    //고등학생, 대학생, 취준생, 직장인
    private Target target;

    //모집 인원
    private int number;

    //활동
    private Activity activity;

    public Study toEntity(){
        return Study.builder()
                .city(city)
                .province(province)
                .district(district)
                .target(target)
                .number(number)
                .activity(activity)
                .build();
        }
}
