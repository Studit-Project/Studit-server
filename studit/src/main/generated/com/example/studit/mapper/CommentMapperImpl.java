package com.example.studit.mapper;

import com.example.studit.domain.Comment;
import com.example.studit.domain.Comment.CommentBuilder;
import com.example.studit.dto.CommentResponseDto;
import com.example.studit.dto.CommentResponseDto.CommentResponseDtoBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-18T16:36:56+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentResponseDto toDto(Comment arg0) {
        if ( arg0 == null ) {
            return null;
        }

        CommentResponseDtoBuilder commentResponseDto = CommentResponseDto.builder();

        commentResponseDto.id( arg0.getId() );
        commentResponseDto.content( arg0.getContent() );
        commentResponseDto.localDateTime( arg0.getLocalDateTime() );
        commentResponseDto.userId(arg0.getUser().getId());

        return commentResponseDto.build();
    }

    @Override
    public Comment toEntity(CommentResponseDto arg0) {
        if ( arg0 == null ) {
            return null;
        }

        CommentBuilder comment = Comment.builder();

        comment.localDateTime( arg0.getLocalDateTime() );
        comment.content( arg0.getContent() );

        return comment.build();
    }

    @Override
    public List<CommentResponseDto> toDtoList(List<Comment> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<CommentResponseDto> list = new ArrayList<CommentResponseDto>( arg0.size() );
        for ( Comment comment : arg0 ) {
            list.add( toDto( comment ) );
        }

        return list;
    }

    @Override
    public List<Comment> toEntityList(List<CommentResponseDto> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Comment> list = new ArrayList<Comment>( arg0.size() );
        for ( CommentResponseDto commentResponseDto : arg0 ) {
            list.add( toEntity( commentResponseDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(CommentResponseDto arg0, Comment arg1) {
        if ( arg0 == null ) {
            return;
        }
    }
}
