package com.example.studit.mapper;

import com.example.studit.domain.Posting;
import com.example.studit.domain.Posting.PostingBuilder;
import com.example.studit.domain.User;
import com.example.studit.dto.PostingDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.example.studit.dto.UserInfoDto.UserInfoDtoBuilder;


import com.example.studit.dto.UserInfoDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-12T15:29:31+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class PostingMapperImpl implements PostingMapper {

    @Override
    public PostingDto toDto(Posting arg0) {
        if ( arg0 == null ) {
            return null;
        }

        PostingDto postingDto = new PostingDto();

        postingDto.setId( arg0.getId() );
        postingDto.setCategory( arg0.getCategory() );
        postingDto.setLocalDateTime( arg0.getLocalDateTime() );
        postingDto.setContent( arg0.getContent() );

        postingDto.setUserInfoDto(UserToUserInfoDto(arg0.getUser()));

        return postingDto;
    }

    protected UserInfoDto UserToUserInfoDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserInfoDtoBuilder userInfoDto = UserInfoDto.builder();

        userInfoDto.user( user );

        return userInfoDto.build();
    }

    @Override
    public Posting toEntity(PostingDto arg0) {
        if ( arg0 == null ) {
            return null;
        }

        PostingBuilder posting = Posting.builder();

        posting.category( arg0.getCategory() );
        posting.localDateTime( arg0.getLocalDateTime() );
        posting.content( arg0.getContent() );

        return posting.build();
    }

    @Override
    public List<PostingDto> toDtoList(List<Posting> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<PostingDto> list = new ArrayList<PostingDto>( arg0.size() );
        for ( Posting posting : arg0 ) {
            list.add( toDto( posting ) );
        }

        return list;
    }

    @Override
    public List<Posting> toEntityList(List<PostingDto> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Posting> list = new ArrayList<Posting>( arg0.size() );
        for ( PostingDto postingDto : arg0 ) {
            list.add( toEntity( postingDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(PostingDto arg0, Posting arg1) {
        if ( arg0 == null ) {
            return;
        }
    }
}
