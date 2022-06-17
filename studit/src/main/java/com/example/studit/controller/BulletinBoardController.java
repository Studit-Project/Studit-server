package com.example.studit.controller;

import com.example.studit.config.swagger.BaseResponse;
import com.example.studit.domain.bulletin.dto.GetAllRes;
import com.example.studit.domain.bulletin.dto.GetDetailRes;
import com.example.studit.domain.bulletin.dto.PostReq;
import com.example.studit.service.BulletinBoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study/{studyId}/bulletin")
public class BulletinBoardController {

    private final BulletinBoardService bulletinBoardService;

    @ApiOperation("스터디룸 내 모든 게시물 보기")
    @GetMapping("")
    public BaseResponse<List<GetAllRes>> getList(@PathVariable(name = "studyId") Long studyId){
        return new BaseResponse<List<GetAllRes>>(bulletinBoardService.getAll(studyId));
    }

    @ApiOperation("게시물 상세 보기")
    @GetMapping("/{bulletinId}")
    public BaseResponse<GetDetailRes> getOne(@PathVariable(name = "studyId") Long studyId, @PathVariable(name = "bulletinId") Long bulletinId){
        return new BaseResponse<GetDetailRes>(bulletinBoardService.getOne(bulletinId));
    }

    @ApiOperation("게시물 작성")
    @PostMapping("/new")
    public BaseResponse<Long> createBoard(@PathVariable(name = "studyId") Long studyId, @RequestBody PostReq postReq){
        return new BaseResponse<Long>(bulletinBoardService.save(studyId, postReq));
    }

    @ApiOperation("게시물 수정")
    @PatchMapping("/{bulletinId}")
    public BaseResponse<String> updateBoard(@PathVariable(name = "studyId") Long studyId, @PathVariable(name = "bulletinId") Long bulletinId, @RequestBody PostReq postReq){
        bulletinBoardService.updateBoard(bulletinId, postReq);
        return new BaseResponse<>("");
    }

    @ApiOperation("게시물 지우기")
    @DeleteMapping("/{bulletinId}")
    public BaseResponse<String> deleteBoard(@PathVariable(name = "studyId") Long studyId, @PathVariable(name = "bulletinId") Long bulletinId){
        bulletinBoardService.delete(bulletinId);
        return new BaseResponse<String>("");
    }

    @ApiOperation("공지사항 설정")
    @PatchMapping("/{bulletinId}/announcement")
    public BaseResponse<String> updateAnnouncement(@PathVariable(name = "studyId") Long studyId, @PathVariable(name = "bulletinId") Long bulletinId){

        return new BaseResponse<String>(bulletinBoardService.updateAnnouncement(studyId, bulletinId));
    }

}
