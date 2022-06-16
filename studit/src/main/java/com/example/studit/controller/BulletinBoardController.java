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


}
