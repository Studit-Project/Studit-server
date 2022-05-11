package com.example.studit.mapper;

import com.example.studit.domain.User;
import com.example.studit.dto.UserInfoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper <UserInfoDto, User> {
}
