package com.example.studit.mapper;

import com.example.studit.domain.study.Study;
import com.example.studit.dto.StudyManageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudyMapper extends GenericMapper<StudyManageDto, Study>{
}
