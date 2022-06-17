package com.example.studit.controller;

import com.example.studit.config.swagger.BaseResponse;
import com.example.studit.domain.comment.dto.CommentRequestDto;
import com.example.studit.domain.comment.dto.PatchCommentReq;
import com.example.studit.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("포스팅 댓글 수정")
    @PatchMapping("/posting/{postingId}/comment/{commentId}")
    public BaseResponse<Long> updatePostingComment(@PathVariable(name = "postingId") Long postingId, @PathVariable(name = "commentId") Long commentId, @RequestBody PatchCommentReq patchCommentReq) {
        return new BaseResponse<Long>(commentService.updateComment(commentId, patchCommentReq));
    }

    @ApiOperation("스터디 내부 게시판 댓글 수정")
    @PatchMapping("/study/{studyId}/bulletin/{bulletinId}/comment/{commentId}")
    public BaseResponse<Long> updateBulletinComment(@PathVariable(name = "studyId") Long studyId, @PathVariable(name = "bulletinId") Long bulletinId, @PathVariable(name = "commentId") Long commentId, @RequestBody PatchCommentReq patchCommentReq) {
        return new BaseResponse<Long>(commentService.updateComment(commentId, patchCommentReq));
    }

    @ApiOperation("포스팅 댓글 삭제")
    @DeleteMapping("/posting/{postingId}/comment/{commentId}")
    public BaseResponse<String> deletePostingComment(@PathVariable(name = "postingId") Long postingId, @PathVariable(name = "commentId") Long commentId){
        commentService.delete(commentId);
        return new BaseResponse<String>("");
    }

    @ApiOperation("스터디 내부 게시판 댓글 삭제")
    @DeleteMapping("/study/{studyId}/bulletin/{bulletinId}/comment/{commentId}")
    public BaseResponse<String> deleteBulletinComment(@PathVariable(name = "studyId") Long studyId, @PathVariable(name = "bulletinId") Long bulletinId, @PathVariable(name = "commentId") Long commentId) {
        commentService.delete(commentId);
        return new BaseResponse<>("");
    }

}
