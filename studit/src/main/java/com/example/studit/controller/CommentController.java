package com.example.studit.controller;

import com.example.studit.config.swagger.BaseResponse;
import com.example.studit.domain.comment.dto.CommentRequestDto;
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

    @ApiOperation("포스팅 댓글 달기")
    @PostMapping("/posting/{postingId}/new-comment")
    public BaseResponse<Long> createComment(@PathVariable("postingId") Long postingId, @RequestBody CommentRequestDto commentRequestDto){
        return new BaseResponse<Long>(commentService.save(postingId, commentRequestDto));
    }

    @ApiOperation("내부 게시판 댓글 달기")
    @PostMapping("/study/{studyId}/bulletin/{bulletinId}")
    public BaseResponse<Long> createBulletinComment(@PathVariable(name = "studyId") Long studyId, @PathVariable(name = "bulletinId") Long bulletinId, @RequestBody CommentRequestDto content){
        return new BaseResponse<Long>(commentService.saveBulletinComment(bulletinId, content));
    }
}
