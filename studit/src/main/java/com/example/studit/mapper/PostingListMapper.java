package com.example.studit.mapper;

import com.example.studit.domain.Posting;
import com.example.studit.dto.PostingListDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostingListMapper extends GenericMapper<PostingListDto, Posting> {
}
