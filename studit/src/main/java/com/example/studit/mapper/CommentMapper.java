package com.example.studit.mapper;

import com.example.studit.domain.Comment;
import com.example.studit.dto.CommentResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper extends GenericMapper<CommentResponseDto, Comment> {
}
