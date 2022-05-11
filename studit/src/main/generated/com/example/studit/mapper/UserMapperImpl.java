package com.example.studit.mapper;

import com.example.studit.domain.User;
import com.example.studit.domain.User.UserBuilder;
import com.example.studit.dto.UserInfoDto;
import com.example.studit.dto.UserInfoDto.UserInfoDtoBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-10T17:15:51+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserInfoDto toDto(User arg0) {
        if ( arg0 == null ) {
            return null;
        }

        UserInfoDtoBuilder userInfoDto = UserInfoDto.builder();

        return userInfoDto.build();
    }

    @Override
    public User toEntity(UserInfoDto arg0) {
        if ( arg0 == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.userName( arg0.getUserName() );
        user.email( arg0.getEmail() );

        return user.build();
    }

    @Override
    public List<UserInfoDto> toDtoList(List<User> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<UserInfoDto> list = new ArrayList<UserInfoDto>( arg0.size() );
        for ( User user : arg0 ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public List<User> toEntityList(List<UserInfoDto> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( arg0.size() );
        for ( UserInfoDto userInfoDto : arg0 ) {
            list.add( toEntity( userInfoDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(UserInfoDto arg0, User arg1) {
        if ( arg0 == null ) {
            return;
        }
    }
}
