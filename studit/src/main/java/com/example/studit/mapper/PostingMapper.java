package com.example.studit.mapper;

import com.example.studit.domain.Posting;
import com.example.studit.dto.PostingDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostingMapper extends GenericMapper<PostingDto, Posting> {
}
