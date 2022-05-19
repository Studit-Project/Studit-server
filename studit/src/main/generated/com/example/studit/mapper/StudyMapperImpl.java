package com.example.studit.mapper;

import com.example.studit.domain.study.Activity;
import com.example.studit.domain.study.MyStudy;
import com.example.studit.domain.study.ParticipatedStudy;
import com.example.studit.domain.study.Study;
import com.example.studit.domain.study.Study.StudyBuilder;
import com.example.studit.dto.StudyManageDto;
import com.example.studit.dto.UserInfoDto;
import com.example.studit.dto.UserInfoDto.UserInfoDtoBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-10T17:15:52+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class StudyMapperImpl implements StudyMapper {

    @Override
    public StudyManageDto toDto(Study arg0) {
        if ( arg0 == null ) {
            return null;
        }

//        Long id = null;
        String name = null;
        String introduction = null;
        int number = 0;
        int currentNum = 0;
        UserInfoDto leader = null;
        Activity activity = null;

//        id = arg0.getId();
        name = arg0.getName();
        introduction = arg0.getIntroduction();
        number = arg0.getNumber();
        currentNum = arg0.getCurrentNum();
        leader = myStudyToUserInfoDto( arg0.getLeader() );
        activity = arg0.getActivity();

        List<UserInfoDto> participatedMembers = null;
        participatedMembers = participatedStudyToUserInfoDto(arg0.getParticipatedMembers());

        StudyManageDto studyManageDto = new StudyManageDto( name, introduction, number, currentNum, leader, activity, participatedMembers );

        return studyManageDto;
    }

    private List<UserInfoDto> participatedStudyToUserInfoDto(List<ParticipatedStudy> participatedMembers) {
        if ( participatedMembers == null ) {
            return null;
        }

        UserInfoDtoBuilder userInfoDto = UserInfoDto.builder();

        List<UserInfoDto> userInfoDtoList = new ArrayList<>();
        for(int i = 0; i < participatedMembers.size(); i++) {
            userInfoDto.user(participatedMembers.get(i).getUser());
            userInfoDtoList.add(userInfoDto.build());
        }

        return userInfoDtoList;
    }

    @Override
    public Study toEntity(StudyManageDto arg0) {
        if ( arg0 == null ) {
            return null;
        }

        StudyBuilder study = Study.builder();

        study.number( arg0.getNumber() );
        study.activity( arg0.getActivity() );

        return study.build();
    }

    @Override
    public List<StudyManageDto> toDtoList(List<Study> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<StudyManageDto> list = new ArrayList<StudyManageDto>( arg0.size() );
        for ( Study study : arg0 ) {
            list.add( toDto( study ) );
        }

        return list;
    }

    @Override
    public List<Study> toEntityList(List<StudyManageDto> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Study> list = new ArrayList<Study>( arg0.size() );
        for ( StudyManageDto studyManageDto : arg0 ) {
            list.add( toEntity( studyManageDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(StudyManageDto arg0, Study arg1) {
        if ( arg0 == null ) {
            return;
        }
    }

    protected UserInfoDto myStudyToUserInfoDto(MyStudy myStudy) {
        if ( myStudy == null ) {
            return null;
        }

        UserInfoDtoBuilder userInfoDto = UserInfoDto.builder();

        userInfoDto.user( myStudy.getUser() );

        return userInfoDto.build();
    }
}
