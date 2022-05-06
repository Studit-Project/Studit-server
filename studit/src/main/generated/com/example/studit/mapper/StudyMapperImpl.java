package com.example.studit.mapper;

import com.example.studit.domain.User;
import com.example.studit.domain.User.UserBuilder;
import com.example.studit.domain.study.Activity;
import com.example.studit.domain.study.MyStudy;
import com.example.studit.domain.study.ParticipatedStudy;
import com.example.studit.domain.study.Study;
import com.example.studit.domain.study.Study.StudyBuilder;
import com.example.studit.dto.StudyManageDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2022-05-06T17:26:18+0900",
        comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class StudyMapperImpl implements StudyMapper {
    @Override
    public StudyManageDto toDto(Study e) {
        if ( e == null ) {
            return null;
        }

        String name = null;
        String introduction = null;
        int number = 0;
        int currentNum = 0;
        User leader = null;
        Activity activity = null;

        name = e.getName();
        introduction = e.getIntroduction();
        number = e.getNumber();
        currentNum = e.getCurrentNum();
        leader = myStudyToUser( e.getLeader() );
        activity = e.getActivity();

        Long leaderId = leader.getId();

        List<ParticipatedStudy> followers = new ArrayList<>();
        followers = e.getParticipatedMembers();
        List<Long> followerId = new ArrayList<>();

        for(int i = 0; i < followers.size(); i++){
            followerId.add(followers.get(i).getId());
        }

        StudyManageDto studyManageDto = new StudyManageDto( name, introduction, number, currentNum, leaderId, activity, followerId );

        return studyManageDto;
    }

    @Override
    public Study toEntity(StudyManageDto d) {
        if ( d == null ) {
            return null;
        }

        StudyBuilder study = Study.builder();

        study.number( d.getNumber() );
        study.activity( d.getActivity() );

        return study.build();
    }

    @Override
    public List<StudyManageDto> toDtoList(List<Study> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StudyManageDto> list = new ArrayList<StudyManageDto>( entityList.size() );
        for ( Study study : entityList ) {
            list.add( toDto( study ) );
        }

        return list;
    }

    @Override
    public List<Study> toEntityList(List<StudyManageDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Study> list = new ArrayList<Study>( dtoList.size() );
        for ( StudyManageDto studyManageDto : dtoList ) {
            list.add( toEntity( studyManageDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(StudyManageDto dto, Study entity) {
        if ( dto == null ) {
            return;
        }
    }

    protected User myStudyToUser(MyStudy myStudy) {
        if ( myStudy == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        return user.build();
    }

}
