package com.example.studit.mapper;

import com.example.studit.domain.Posting;
import com.example.studit.domain.Posting.PostingBuilder;
import com.example.studit.dto.PostingListDto;
import com.example.studit.dto.PostingListDto.PostingListDtoBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-18T17:42:51+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class PostingListMapperImpl implements PostingListMapper {

    @Override
    public PostingListDto toDto(Posting arg0) {
        if ( arg0 == null ) {
            return null;
        }

        PostingListDto postingListDto = new PostingListDto();
        postingListDto.setId(arg0.getId());
        postingListDto.setTitle(arg0.getTitle());
        postingListDto.setLocalDateTime(arg0.getLocalDateTime());
        postingListDto.setUserId(arg0.getUser().getId());

//        PostingListDtoBuilder postingListDto = PostingListDto.builder()
//                .id(arg0.getId())
//                .title(arg0.getTitle())
//                .userId(arg0.getUser().getId())
//                .localDateTime(arg0.getLocalDateTime());

        return postingListDto;
    }

    @Override
    public Posting toEntity(PostingListDto arg0) {
        if ( arg0 == null ) {
            return null;
        }

        PostingBuilder posting = Posting.builder();

        posting.title( arg0.getTitle() );
        posting.localDateTime( arg0.getLocalDateTime() );

        return posting.build();
    }

    @Override
    public List<PostingListDto> toDtoList(List<Posting> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<PostingListDto> list = new ArrayList<PostingListDto>( arg0.size() );
        for ( Posting posting : arg0 ) {
            list.add( toDto( posting ) );
        }

        return list;
    }

    @Override
    public List<Posting> toEntityList(List<PostingListDto> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Posting> list = new ArrayList<Posting>( arg0.size() );
        for ( PostingListDto postingListDto : arg0 ) {
            list.add( toEntity( postingListDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(PostingListDto arg0, Posting arg1) {
        if ( arg0 == null ) {
            return;
        }
    }
}
