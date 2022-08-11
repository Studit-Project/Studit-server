package com.example.studit.domain.challenge.dto;

import com.example.studit.domain.challenge.Subject;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChallengeReq {
    private String title;
    private String content;
    private Subject subject;
    private List<MultipartFile> image = new ArrayList<>();
}
