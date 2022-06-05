package com.example.studit.domain.study;

import com.example.studit.domain.BaseEntity;
import com.example.studit.domain.posting.Province;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
public class Region extends BaseEntity {
    //도 -> 특별시, 광역시 포함
    @Enumerated(EnumType.STRING)
    private Province province;

    //시 -> 특별시, 광역시 미포함
    private String city;

    //지구, 구
    private String district;

    protected Region(){
    }

    public Region(Province province){
        this.province = province;
    }

    public Region(Province province, String city){
        this.province = province;
        this.city = city;
    }

    public Region(Province province, String city, String district){
        this.province = province;
        this.city = city;
        this.district = district;
    }
}
