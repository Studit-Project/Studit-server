package com.example.studit.domain.study;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Region {
    //도 -> 특별시, 광역시 포함
    private String province;

    //시 -> 특별시, 광역시 미포함
    private String city;

    //지구, 구
    private String district;

    protected Region(){
    }

    public Region(String province){
        this.province = province;
    }

    public Region(String province, String city){
        this.province = province;
        this.city = city;
    }

    public Region(String province, String city, String district){
        this.province = province;
        this.city = city;
        this.district = district;
    }
}
