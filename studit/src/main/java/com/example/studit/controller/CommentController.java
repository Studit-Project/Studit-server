package com.example.studit.controller;

import com.example.studit.config.swagger.BaseResponse;
import com.example.studit.dto.CommentRequestDto;
import com.example.studit.service.CommentService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("댓글 달기")
    @PostMapping("/posting/{postingId}")
    public BaseResponse<Long> createComment(@PathVariable("postingId") Long postingId, @RequestBody CommentRequestDto commentRequestDto){
        return new BaseResponse<Long>(commentService.save(postingId, commentRequestDto));
    }
}
