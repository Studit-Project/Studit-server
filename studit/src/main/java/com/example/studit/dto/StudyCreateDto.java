package com.example.studit.dto;

import com.example.studit.domain.User;
import com.example.studit.domain.study.Region;
import com.example.studit.domain.study.Study;
import com.example.studit.domain.study.Target;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyCreateDto {
    //지역
    String province;
    String city;
    String district;

    //고등학생, 대학생, 취준생, 직장인
    Target target;

    public Study toEntity(){
        return Study.builder()
                .city(city)
                .province(province)
                .district(district)
                .target(target)
                .build();
        }
}
