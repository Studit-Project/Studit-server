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
    @GetMapping("/{bulletin_id}")
    public BaseResponse<GetDetailRes> getOne(@PathVariable(name = "studyId") Long studyId, @PathVariable(name = "bulletin_id") Long bulletinId){
        return new BaseResponse<GetDetailRes>(bulletinBoardService.getOne(bulletinId));
    }
}
