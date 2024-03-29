package com.example.studit.controller;

import com.example.studit.config.exception.BaseResponse;
import com.example.studit.domain.bulletin.dto.GetDetailRes;
import com.example.studit.domain.enumType.StudyStatus;
import com.example.studit.domain.study.dto.GetInteriorRes;
import com.example.studit.domain.study.dto.PatchAddReq;
import com.example.studit.domain.study.dto.PostCreateReq;
import com.example.studit.domain.study.dto.GetManageRes;
import com.example.studit.service.BulletinBoardService;
import com.example.studit.service.StudyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyController {

    @Autowired
    private final StudyService studyService;
    private final BulletinBoardService bulletinBoardService;

    //스터디룸 개설
    @ApiOperation("스터디 방 개설")
    @PostMapping("/management/new")
    public BaseResponse<Long> createStudy(@RequestBody PostCreateReq studyCreateReq){
        return new BaseResponse<>(studyService.save(studyCreateReq));
    }

    //스터디 관리
    @ApiOperation("스터디 관리 페이지")
    @GetMapping("/management")
    public BaseResponse<List<GetManageRes>> studyManage(){
        return new BaseResponse<List<GetManageRes>>(studyService.findIndividualStudies());
    }

    //스터디룸
    @ApiOperation("스터디룸")
    @GetMapping("/{studyId}")
    public BaseResponse<GetInteriorRes> getStudyRoom(@PathVariable("studyId") Long studyId){
        return new BaseResponse<GetInteriorRes>(studyService.getOne(studyId));
    }

    //스터디원 초대
    @ApiOperation("스터디원 초대")
    @PostMapping("/{studyId}/recruitment")
    public BaseResponse<Long> inviteFollower(@PathVariable(name = "studyId") Long studyId, @RequestBody PatchAddReq nickname) throws IOException {
        return new BaseResponse<Long>(studyService.inviteFollower(studyId, nickname));
    }

    //스터디원 추가
    @ApiOperation("스터디원 추가")
    @PatchMapping("/{studyId}")
    public BaseResponse<Long> addStudyOne(@PathVariable("studyId") Long studyId, @RequestBody PatchAddReq patchAddReq){
        return new BaseResponse<Long>(studyService.addStudyOne(studyId, patchAddReq));
    }

    //스터디원 강퇴
    @ApiOperation("강퇴")
    @DeleteMapping("/{studyId}/expulsion/{followerId}")
    public BaseResponse<String> expel(@PathVariable(name = "studyId") Long studyId, @PathVariable(name = "followerId") Long followerId) throws IOException {
        studyService.expelFollower(studyId, followerId);
        return new BaseResponse<String>("");
    }

    @ApiOperation("스터디 삭제")
    @DeleteMapping("/{studyId}")
    public BaseResponse<String> deleteStudy(@PathVariable(name = "studyId") Long studyId) {
        studyService.delete(studyId);
        return new BaseResponse<String>("");
    }

    @ApiOperation("스터디 모집 상태 변경")
    @PatchMapping("/{studyId}/status")
    public BaseResponse<String> updateStatus(@PathVariable(name = "studyId") Long studyId, @RequestParam StudyStatus studyStatus) {
        studyService.updateStatus(studyId, studyStatus);
        return new BaseResponse<String>("");
    }

    @ApiOperation("상위 공지사항 클릭")
    @GetMapping("{studyId}/{bulletinId}")
    public BaseResponse<GetDetailRes> getAnnouncement(@PathVariable(name = "studyId") Long studyId, @PathVariable(name = "bulletinId") Long bulletinId) {
        return new BaseResponse<GetDetailRes>(bulletinBoardService.getOne(bulletinId));
    }

    @ApiOperation("스터디 나가기")
    @PatchMapping("/{studyId}/exit")
    public BaseResponse<String> leaveStudy(@PathVariable(name = "studyId") Long studyId){
        return new BaseResponse<String>(studyService.leaveStudy(studyId));
    }
}
