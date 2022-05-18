package com.example.studit.controller;

import com.example.studit.dto.CommentRequestDto;
import com.example.studit.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    @Autowired
    private final CommentService commentService;

    @PostMapping("/posting/{postingId}")
    public Long createComment(@PathVariable("postingId") Long postingId, @RequestBody CommentRequestDto commentRequestDto){
        commentRequestDto.setPostingId(postingId);
        return commentService.save(commentRequestDto);
    }
}
